/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import model.Message;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class SubscriptionServer {

    private static ArrayList<Reader> readerList = new ArrayList<Reader>();
    private int port;
    private Listener listener;
    private Executor executor;

    public SubscriptionServer(int p, int interval) {
        port = p;
        executor = Executors.newSingleThreadExecutor();
        listener = new Listener(port);
        executor.execute(listener);
        Timer timer = new Timer(true);
        timer.schedule(new Pinger(), new Date(System.currentTimeMillis() + 10000), interval * 60000);
    }

    public void broadcastMessage(String msg, int cat) {

        

        Message messsage = new Message(new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                msg, cat);
        

    }

    public static ArrayList<Reader> getReaderList() {
        return readerList;
    }
}
