/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class ConnectionHandler implements Runnable {

    private Socket socket;
    private SubscriptionServer ss;
    private Reader rdr;

    public ConnectionHandler(Socket s, SubscriptionServer ss) {
        this.socket = s;
        this.ss = ss;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            line = reader.readLine();
            rdr = new Reader(socket.getInetAddress().getHostAddress(), line, socket);
            ss.add(rdr);
            if (reader.readLine() == null) {
                ss.remove(rdr);
                ss.changed();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            ss.remove(rdr);
            ss.changed();
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
