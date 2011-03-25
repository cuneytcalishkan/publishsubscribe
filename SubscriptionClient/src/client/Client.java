/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Subscriber;

/**
 *
 * @author Natan
 */
public class Client {

    Configure configure;
    Ponger ponger;
    Connection con;

    Subscriber subscriber;

    public Client() {
        try {
            configure = new Configure();
            ponger = new Ponger(Integer.parseInt(configure.getProperty("pongPort")));
            con = ConnectDB.getConnection();
            //get list
            //get server address from db
            //connect server. if fails try once in a while.
            //
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




}
