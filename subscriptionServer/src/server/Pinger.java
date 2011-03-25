/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import model.Reader;

/**
 * @author CUNEYT
 */
public class Pinger extends TimerTask {

    public void run() {
        ArrayList<Reader> removal = new ArrayList<Reader>();
        for (Reader reader : SubscriptionServer.getReaderList()) {
            try {
                Socket s = new Socket(reader.getAddress(), reader.getPingPort());
                s.close();
            } catch (UnknownHostException ex) {
                removal.add(reader);
            } catch (IOException ioe) {
                removal.add(reader);
                SLogger.getLogger().log(Level.SEVERE, ioe.getMessage(), ioe);
            }
        }
        for (Reader rem : removal) {
            SubscriptionServer.getReaderList().remove(rem);
        }
    }
}
