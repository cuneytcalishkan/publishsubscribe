/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natan
 */
public class Subscriber {

    List<Message> sinyaller;
    List<Message> yorumlar;

    public Subscriber() {
        sinyaller = new ArrayList<Message>();
        yorumlar = new ArrayList<Message>();
    }

    

}
