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

    List<Message> signals;
    List<Message> comments;

    public Subscriber() {
        signals = new ArrayList<Message>();
        comments = new ArrayList<Message>();
    }

    public void addSignal(Message mes){
        signals.add(mes);
    }

    public void addComment(Message mes){
        comments.add(mes);
    }

}
