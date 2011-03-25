/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CUNEYT
 */
public class SLogger {

    public static Logger getLogger() {

        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("subscription.client");
        try {
            FileHandler handler = new FileHandler("resources/log.txt", true);
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(SLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logger;
    }
}
