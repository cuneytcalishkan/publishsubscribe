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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                Logger logger = Logger.getLogger(Pinger.class.getName());
                try {
                    FileHandler handler = new FileHandler("resources/log.txt", true);
                    logger.addHandler(handler);
                    logger.log(Level.SEVERE, ioe.getMessage(), ioe);
                } catch (IOException ex) {
                    Logger.getLogger(Pinger.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Pinger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Reader rem : removal) {
            SubscriptionServer.getReaderList().remove(rem);
        }
    }
}
