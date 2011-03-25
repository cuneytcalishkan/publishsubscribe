/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author Natan
 */
public class Ponger implements Runnable {

    private int port;
    private ServerSocket ser;

    public Ponger(int port) {
        this.port = port;
    }

    public void run() {
        try {
            ser = new ServerSocket(port);
            Socket sock = ser.accept();
            sock.close();
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    public void finish() throws IOException{
        ser.close();
    }

}
