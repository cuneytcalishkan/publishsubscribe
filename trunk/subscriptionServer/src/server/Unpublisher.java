/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author CUNEYT
 */
public class Unpublisher implements Runnable {

    private SubscriptionServer ss;

    public Unpublisher(SubscriptionServer ss) {
        this.ss = ss;
    }

    public void run() {
        try {
            String sql = "TRUNCATE TABLE serverURL";
            Connection connection = ConnectDB.getConnection(ss.getHost(), ss.getUsername(), ss.getPassword());
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
