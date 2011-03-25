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
    private BufferedReader reader;
    private SubscriptionServer ss;

    public ConnectionHandler(Socket s, SubscriptionServer ss) {
        this.socket = s;
        this.ss = ss;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            line = reader.readLine();
            Reader rdr = new Reader(socket.getInetAddress().getHostAddress(), line);
            ss.getReaderList().put(rdr, socket);
            ss.changed();
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
