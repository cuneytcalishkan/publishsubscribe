/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import model.Message;

/**
 *
 * @author CUNEYT
 */
public class MessageModel implements ListModel {

    private List<Message> collection;

    public MessageModel(List<Message> collection) {
        this.collection = collection;
    }

    public int getSize() {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public Object getElementAt(int index) {
        if (collection == null) {
            return null;
        }
        return collection.get(index);
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }

    public List<Message> getCollection() {
        return collection;
    }

    public void setCollection(List<Message> collection) {
        this.collection = collection;
    }
}
