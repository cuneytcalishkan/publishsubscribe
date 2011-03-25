/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.sql.Statement;
import java.sql.Connection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.StringTokenizer;
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
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
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
                subscriber.addMessage(message);
            }

            rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            rs.next();
            if (rs != null) {
                serverURL = rs.getString(2);
                serverPort = rs.getInt(3);
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

            } else {
                //TODO server yok, ne bok yiyeceğiz?
            }
        } catch (UnknownHostException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ser = new ServerSocket(Integer.parseInt(configure.getProperty("port")));
            Socket sock = ser.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String[] line = br.readLine().split("|");
            sock.close();
            String content = line[3];
            for (int i = 4; i < line.length; i++) {
                content += line[i];
            }
            Message mes = new Message(new Date(Long.parseLong(line[0])), new Time(Long.parseLong(line[1])), content, Integer.parseInt(line[2]));
            subscriber.addMessage(mes);
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
