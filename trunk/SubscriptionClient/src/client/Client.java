/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.sql.Statement;
import java.sql.Connection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Message;
import model.Subscriber;
import view.ClientFrame;

/**
 *
 * @author Natan
 */
public class Client extends Observable implements Runnable {

    private Configure configure;
    private Subscriber subscriber;
    private ClientFrame view;
    private Socket sock;
    private String username;
    private String password;

    public Client(String username, String password, ClientFrame view) {
        try {
            configure = new Configure();
        } catch (IOException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Konfigurasyon dosyasına erişilemiyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
        this.username = username;
        this.password = password;
        this.view = view;
    }

    public void connect() {
        try {
            Connection conn = ConnectDB.getConnection(configure.getProperty("dbURL"), username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            if (rs.next()) {
                String serverURL = rs.getString(2);
                int serverPort = rs.getInt(3);
                sock = new Socket(serverURL, serverPort);
                PrintWriter pw = new PrintWriter(sock.getOutputStream());
                pw.println(username);
                pw.flush();
                Thread listenerThread = new Thread(this);
                listenerThread.start();
                view.setConnectionButtonVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Bağlanılacak aktif bir sunucu yok.");
            }
        } catch (IOException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Sunucuya bağlanılamıyor.");
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Veritabanına bağlanılamıyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    public void start() {
        // constructor'a parametre olarak gelecek bağlanma ekranından sonra.
        try {
            Connection conn = ConnectDB.getConnection(configure.getProperty("dbURL"), username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM newsAndComments");
            subscriber = new Subscriber();
            while (rs.next()) {
                Message message = new Message(rs.getDate(4), rs.getTime(5), rs.getString(2), rs.getInt(3));
                subscriber.addMessage(message);
            }

            rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            if (rs.next()) {
                String serverURL = rs.getString(2);
                int serverPort = rs.getInt(3);
                sock = new Socket(serverURL, serverPort);
                PrintWriter pw = new PrintWriter(sock.getOutputStream());
                pw.println(username);
                pw.flush();
                Thread listenerThread = new Thread(this);
                listenerThread.start();

            } else {
                JOptionPane.showMessageDialog(null, "Bağlanılacak aktif bir sunucu yok.\nEski mesajlar listeleniyor.");
                view.setConnectionButtonVisible(true);
            }
            conn.close();
        } catch (IOException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Sunucuya bağlanılamıyor.\nEski mesajlar listeleniyor.");
            view.setConnectionButtonVisible(true);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Veritabanına bağlanılamıyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String lineString = br.readLine();
                String[] line = lineString.split(":");
                String content = line[3];
                for (int i = 4; i < line.length; i++) {
                    content += line[i];
                }

                while ((lineString = br.readLine()) != null && !lineString.equals("/EOL/")) {
                    content += "\r\n" + lineString;
                }
                Message mes = new Message(new Date(Long.parseLong(line[0])), new Time(Long.parseLong(line[1])), content, Integer.parseInt(line[2]));
                subscriber.addMessage(mes);
                changed();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Sunucuyla bağlantı kesildi.\n" + ex);
            view.setConnectionButtonVisible(true);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }

    private void changed() {
        setChanged();
        notifyObservers();
    }

    public void finish() {
        try {
            sock.close();
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
