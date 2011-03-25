/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionTest;

import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.SLogger;

/**
 *
 * @author CUNEYT
 */
public class LoggerTest {

    public LoggerTest() {
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
    @Test
    public void helloException() {
        try {
            throw new Exception("test exception to be logged");
        } catch (Exception ex) {
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }

    }
}
