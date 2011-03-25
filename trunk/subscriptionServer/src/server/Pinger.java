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

    private SubscriptionServer ss;

    public Pinger(SubscriptionServer ss) {
        this.ss = ss;
    }

    public void run() {
        ArrayList<Reader> removal = new ArrayList<Reader>();
        for (Reader reader : ss.getReaderList()) {
            try {
                Socket s = new Socket(reader.getAddress(), reader.getPingPort());
                s.close();
            } catch (UnknownHostException ex) {
                SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
                removal.add(reader);
            } catch (IOException ioe) {
                removal.add(reader);
                SLogger.getLogger().log(Level.SEVERE, ioe.getMessage());
            }
        }
        ss.getReaderList().removeAll(removal);
        ss.changed();
    }
}
