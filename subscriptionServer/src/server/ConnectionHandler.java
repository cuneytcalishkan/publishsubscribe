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
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Reader rdr = new Reader(socket.getInetAddress().getHostAddress(),
                        Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]),
                        tokens[2]);
                ss.addReader(rdr);
                ss.changed();
                System.out.println(rdr + " has been added to reader list");
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
