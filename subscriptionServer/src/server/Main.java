/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author CUNEYT
 */
public class Main {

    public static void main(String[] args) {
        try {
            Configure config = new Configure();
            int serverPort = Integer.parseInt(config.getProperty("serverPort"));
            String username = config.getProperty("dbUsername");
            String password = config.getProperty("dbPassword");
            String host = config.getProperty("dbURL");
            SubscriptionServer ss = new SubscriptionServer(serverPort, username, password, host);
            Thread.sleep(3000);
            ss.broadcastMessage("server sending message to client", 2);
            Object sync = new Object();
            synchronized (sync) {
                sync.wait();
            }
        } catch (InterruptedException e) {
            SLogger.getLogger().log(Level.SEVERE, e.getMessage());
        } catch (IOException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }
}
