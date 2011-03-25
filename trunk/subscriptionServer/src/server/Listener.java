/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 *
 * @author CUNEYT
 */
public class Listener implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private Executor executor;
    private SubscriptionServer ss;

    public Listener(int port, SubscriptionServer ss) {
        this.port = port;
        this.ss = ss;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket s = serverSocket.accept();
                ConnectionHandler ch = new ConnectionHandler(s, ss);
                executor.execute(ch);
            }
        } catch (IOException ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
