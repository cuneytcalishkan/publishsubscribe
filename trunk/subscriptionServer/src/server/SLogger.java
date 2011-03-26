/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CUNEYT
 */
public class SLogger {

    private static final Logger logger;

    static {
        logger = Logger.getLogger("subscription.server");
        try {
            FileHandler handler = new FileHandler("log.txt", true);
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(SLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
