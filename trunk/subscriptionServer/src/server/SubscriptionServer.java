/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class SubscriptionServer extends Observable {

    private ArrayList<Reader> readerList = new ArrayList<Reader>();
    private int port;
    private Listener listener;
    private Executor executor;
    private String username;
    private String password;

    public SubscriptionServer(int p, String uname, String pwd) {
        port = p;
        username = uname;
        password = pwd;
        executor = Executors.newCachedThreadPool();
        init();
    }

    private void init() {
        listener = new Listener(port, this);
        executor.execute(listener);
        publishIPOnDB();
    }

    public void unpublishIPOnDB() {
        executor.execute(new Unpublisher(this));

    }

    private void publishIPOnDB() {
        try {
            Connection connection = ConnectDB.getConnection(username, password);
            String truncate = "TRUNCATE TABLE serverURL";
            String sql = "INSERT INTO serverURL (`url`,`port`) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(truncate);
            ps.executeUpdate();
            ps = connection.prepareStatement(sql);

            URL url = new java.net.URL("http://whatismyip.com/automation/n09230945.asp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            ps.setString(1, br.readLine());
            ps.setInt(2, port);
            ps.executeUpdate();
            connection.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Internet bağlantısı yok veya whatismyip'ye bağlanılamıyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Veritabanına bağlanılamıyor.\n" + ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }

    public void broadcastMessage(String msg, int cat) {
        executor.execute(new MessageSender(this, msg, cat));
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Reader> getReaderList() {
        return readerList;
    }

    public void setReaderList(ArrayList<Reader> readerList) {
        this.readerList = readerList;
    }

    public boolean removeAll(Collection<?> c) {
        return readerList.removeAll(c);
    }

    public boolean remove(Reader o) {
        return readerList.remove(o);
    }
}
