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

    public ConnectionHandler(Socket s) {
        this.socket = s;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Reader rdr = new Reader(socket.getInetAddress().toString(),
                        Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]),
                        tokens[2]);
                SubscriptionServer.getReaderList().add(rdr);
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
