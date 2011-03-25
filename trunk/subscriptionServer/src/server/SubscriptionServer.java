/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class SubscriptionServer extends Observable {

    private ArrayList<Reader> readerList = new ArrayList<Reader>();
    private int port;
    private int pingInterval;
    private Listener listener;
    private Executor executor;
    private String username;
    private String password;

    public SubscriptionServer(int p, int interval, String uname, String pwd) {
        port = p;
        pingInterval = interval;
        username = uname;
        password = pwd;
        executor = Executors.newCachedThreadPool();
        init();
    }

    private void init() {
        listener = new Listener(port, this);
        executor.execute(listener);
        Timer timer = new Timer(true);
        timer.schedule(new Pinger(this), new Date(System.currentTimeMillis() + 10000), pingInterval * 60000);
        publishIPOnDB();
    }

    public void unpublishIPOnDB() {
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
            String truncate = "TRUNCATE TABLE serverURL";
            String sql = "INSERT INTO serverURL (`url`,`port`) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(truncate);
            ps.executeUpdate();
            ps = connection.prepareStatement(sql);
            ps.setString(1, InetAddress.getLocalHost().getHostAddress());
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
        executor.execute(new MessageSender(this, msg, cat));
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<Reader> getReaderList() {
        return readerList;
    }

    public void addReader(Reader newReader) {
        readerList.add(newReader);
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

    public int getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(int pingInterval) {
        this.pingInterval = pingInterval;
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
}
