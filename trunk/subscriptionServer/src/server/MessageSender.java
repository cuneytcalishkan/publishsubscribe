/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import model.Message;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class MessageSender implements Runnable {

    private SubscriptionServer ss;
    private String msg;
    private int cat;

    public MessageSender(SubscriptionServer ss, String message, int cat) {
        this.ss = ss;
        this.msg = message;
        this.cat = cat;
    }

    public void run() {
        try {
            ArrayList<Reader> removal = new ArrayList<Reader>();
            Connection connection = ConnectDB.getConnection(ss.getUsername(), ss.getPassword());
            String sql = "INSERT INTO newsAndComments (`content`,`category`,`eDate`,`eTime`) VALUES(?,?,?,?)";
            Date eDate = new Date(System.currentTimeMillis());
            Time eTime = new Time(System.currentTimeMillis());
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, msg);
            st.setInt(2, cat);
            st.setDate(3, eDate);
            st.setTime(4, eTime);
            st.executeUpdate();
            connection.close();
            Message message = new Message(eDate,
                    eTime,
                    msg, cat);

            for (Iterator<Reader> it = ss.getReaderList().keySet().iterator(); it.hasNext();) {
                Reader reader = it.next();
                Socket socket = ss.getReaderList().get(reader);

                try {
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    pw.println(message.toString());
                    pw.println("/EOL/");
                    pw.flush();
                } catch (UnknownHostException ex) {
                    System.out.println(ex);
                    removal.add(reader);
                    SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex);
                    removal.add(reader);
                    SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
                }

            }
            for (Reader reader : removal) {
                ss.getReaderList().remove(reader);
            }
            ss.changed();
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Veritabanına bağlanılamıyor.\n Mesaj veritabanına eklenemedi.");
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
