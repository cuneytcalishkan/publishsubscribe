/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import model.Message;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class SubscriptionServer {

    private static ArrayList<Reader> readerList = new ArrayList<Reader>();
    private int port;
    private Listener listener;
    private Executor executor;
    private String username;
    private String password;

    public SubscriptionServer(int p, int interval, String uname, String pwd) {
        port = p;
        username = uname;
        password = pwd;
        executor = Executors.newSingleThreadExecutor();
        listener = new Listener(port);
        executor.execute(listener);
        Timer timer = new Timer(true);
        timer.schedule(new Pinger(), new Date(System.currentTimeMillis() + 10000), interval * 60000);
        publishIPOnDB();
    }

    public void unpublishIPInDB() {
        try {
            String sql = "TRUNCATE TABLE serverURL";
            Connection connection = ConnectDB.getConnection(username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }

    private void publishIPOnDB() {
        try {
            Connection connection = ConnectDB.getConnection(username, password);
            String sql = "INSERT INTO serverURL (url,port) VALUES(?,?)";
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setString(1, InetAddress.getLocalHost().toString());
            ps.setInt(2, port);
            ps.executeUpdate();
            connection.close();
        } catch (UnknownHostException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }

    public void broadcastMessage(String msg, int cat) {
        try {
            Connection connection = ConnectDB.getConnection(username, password);
            String sql = "INSERT INTO newsAndComments (content,category,eDate,eTime)"
                    + " VALUES (?,?,?,?)";
            Date eDate = new Date(System.currentTimeMillis());
            Time eTime = new Time(System.currentTimeMillis());
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, msg);
            st.setInt(2, cat);
            st.setDate(3, eDate);
            st.setTime(4, eTime);
            st.executeUpdate(sql);
            connection.close();
            Message message = new Message(eDate,
                    eTime,
                    msg, cat);

            for (Reader reader : readerList) {
                try {
                    Socket socket = new Socket(reader.getAddress(), reader.getPort());
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    pw.println(message.toString());
                    pw.flush();
                    pw.close();
                    socket.close();
                } catch (UnknownHostException ex) {
                    SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
                } catch (IOException ex) {
                    SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    public static ArrayList<Reader> getReaderList() {
        return readerList;
    }
}
