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

    public void addMessage(Message message) {
        if (message.getCategory() == Message.SIGNAL) {
            addSignal(message);
        } else {
            addComment(message);
        }
    }

    private void addSignal(Message mes) {
        signals.add(0, mes);
    }

    private void addComment(Message mes) {
        comments.add(0, mes);
    }

    public List<Message> getComments() {
        return comments;
    }

    public List<Message> getSignals() {
        return signals;
    }
}
