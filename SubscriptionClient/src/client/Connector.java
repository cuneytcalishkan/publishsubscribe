/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author CUNEYT
 */
public class Connector extends Thread {

    private String host;
    private String username;
    private String password;
    private Client client;
    private Socket sock;

    public Connector(String host, String username, String password, Client client, Socket sock) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.client = client;
        this.sock = sock;
    }

    @Override
    public void run() {
        client.setConnectionButtonVisible(false);
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection(host, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM serverURL LIMIT 1");
            if (rs.next()) {
                String serverURL = rs.getString(2);
                int serverPort = rs.getInt(3);
                sock = new Socket(serverURL, serverPort);
                client.setSock(sock);
                PrintWriter pw = new PrintWriter(sock.getOutputStream());
                pw.println(username);
                pw.flush();
                Thread listenerThread = new Thread(client);
                listenerThread.start();
                client.setConnectionButtonVisible(false);
                JOptionPane.showMessageDialog(null, "Sunucu ile bağlantı kuruldu!");
            } else {
                JOptionPane.showMessageDialog(null, "Bağlanılacak aktif bir sunucu yok.");
                client.setConnectionButtonVisible(true);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            client.setConnectionButtonVisible(true);
            JOptionPane.showMessageDialog(null, "Sunucuya bağlanılamıyor.");
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex);
            client.setConnectionButtonVisible(true);
            JOptionPane.showMessageDialog(null, "Veritabanına bağlanılamıyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
