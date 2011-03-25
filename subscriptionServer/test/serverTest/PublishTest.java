/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Configure;
import server.SubscriptionServer;

/**
 *
 * @author CUNEYT
 */
public class PublishTest {

    public PublishTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//    @Test
//    public void publishIPOnDB() {
//        try {
//            Configure config = new Configure();
//            String dbUsername = config.getProperty("dbUsername");
//            String dbPassword = config.getProperty("dbPassword");
//            int pingInterval = Integer.parseInt(config.getProperty("pingInterval"));
//            int serverPort = Integer.parseInt(config.getProperty("serverPort"));
//            SubscriptionServer ss = new SubscriptionServer(serverPort, pingInterval, dbUsername, dbPassword);
//        } catch (IOException ex) {
//            Logger.getLogger(PublishTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Test
    public void sendMessage() {
        try {
            Configure config = new Configure();
            String dbUsername = config.getProperty("dbUsername");
            String dbPassword = config.getProperty("dbPassword");
            int pingInterval = Integer.parseInt(config.getProperty("pingInterval"));
            int serverPort = Integer.parseInt(config.getProperty("serverPort"));
            SubscriptionServer ss = new SubscriptionServer(serverPort, pingInterval, dbUsername, dbPassword);
            ss.broadcastMessage("test Message4", 1);
            ss.unpublishIPOnDB();
        } catch (IOException ex) {
            Logger.getLogger(PublishTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
