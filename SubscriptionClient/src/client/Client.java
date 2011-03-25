/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.UnknownHostException;
import java.sql.Statement;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.Subscriber;

/**
 *
 * @author Natan
 */
public class Client implements Runnable {

    private Configure configure;
    private Ponger ponger;
    private Connection conn;
    private String serverURL;
    private int serverPort;
    private Thread listenerThread, pongerThread;
    private Subscriber subscriber;

    public Client() {
        try {
            configure = new Configure();
            ponger = new Ponger(Integer.parseInt(configure.getProperty("pongPort")));
            subscriber = new Subscriber();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        // constructor'a parametre olarak gelecek bağlanma ekranından sonra.
        try {
            pongerThread = new Thread(ponger);
            pongerThread.start();
            String username = "okuyucu";
            String password = "oku123yucu";
            conn = ConnectDB.getConnection(username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM newsAndComments");
            while (rs.next()) {
                Message message = new Message(rs.getDate(4), rs.getTime(5), rs.getString(2), rs.getInt(3));
                if (message.getCategory() == Message.SIGNAL) {
                    subscriber.addSignal(message);
                } else {
                    subscriber.addComment(message);
                }
            }

            rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            rs.next();
            if (rs != null) {
                serverURL = rs.getString(2);
                serverPort = rs.getInt(3);
                try {
                    Socket sock = new Socket(serverURL, serverPort);
                    PrintWriter pw = new PrintWriter(sock.getOutputStream());
                    pw.println(configure.getProperty("port") + ","
                            + configure.getProperty("pongPort") + ","
                            + username);
                    pw.flush();
                    pw.close();
                    sock.close();
                    listenerThread = new Thread(this);
                    listenerThread.start();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //TODO server yok, ne bok yiyeceğiz?
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            ServerSocket ser = new ServerSocket(Integer.parseInt(configure.getProperty("port")));
            Socket sock = ser.accept();
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(Ponger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
