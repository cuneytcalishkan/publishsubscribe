/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class SubscriptionServer {

    private static ArrayList<Reader> readerList = new ArrayList<Reader>();

    public static ArrayList<Reader> getReaderList() {
        return readerList;
    }
}
