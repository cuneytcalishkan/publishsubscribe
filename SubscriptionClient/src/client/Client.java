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
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.Subscriber;

/**
 *
 * @author Natan
 */
public class Client extends Observable implements Runnable {

    private Configure configure;
    private Ponger ponger;
    private Subscriber subscriber;
    private ServerSocket ser;

    public Client() {
        try {
            configure = new Configure();
            subscriber = new Subscriber();

        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    public void start() {
        // constructor'a parametre olarak gelecek bağlanma ekranından sonra.
        try {
            ponger = new Ponger(Integer.parseInt(configure.getProperty("pongPort")));
            Thread pongerThread = new Thread(ponger);
            pongerThread.start();
            String username = "okuyucu";
            String password = "oku123yucu";
            Connection conn = ConnectDB.getConnection(username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM newsAndComments");
            while (rs.next()) {
                Message message = new Message(rs.getDate(4), rs.getTime(5), rs.getString(2), rs.getInt(3));
                subscriber.addMessage(message);
            }

            rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            if (rs.next()) {
                String serverURL = rs.getString(2);
                int serverPort = rs.getInt(3);
                Socket sock = new Socket(serverURL, serverPort);
                PrintWriter pw = new PrintWriter(sock.getOutputStream());
                pw.println(configure.getProperty("port") + ","
                        + configure.getProperty("pongPort") + ","
                        + username);
                pw.flush();
                pw.close();
                sock.close();
                Thread listenerThread = new Thread(this);
                listenerThread.start();

            } else {
                //TODO server yok, ne bok yiyeceğiz?
            }
            conn.close();
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
            ser = new ServerSocket(Integer.parseInt(configure.getProperty("port")));
            while (true) {
                Socket sock = ser.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String[] line = br.readLine().split(":");
                sock.close();
                String content = line[3];
                for (int i = 4; i < line.length; i++) {
                    content += line[i];
                }
                Message mes = new Message(new Date(Long.parseLong(line[0])), new Time(Long.parseLong(line[1])), content, Integer.parseInt(line[2]));
                subscriber.addMessage(mes);
                changed();
                System.out.println("Received: " + mes);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }

    private void changed() {
        setChanged();
        notifyObservers();
    }

    public void finish() {
        try {
            ser.close();
            ponger.finish();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public List<Message> getSignals() {
        return subscriber.getSignals();
    }

    public List<Message> getComments() {
        return subscriber.getComments();
    }
}
