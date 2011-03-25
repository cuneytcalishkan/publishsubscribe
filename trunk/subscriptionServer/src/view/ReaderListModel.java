/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import model.Reader;

/**
 *
 * @author CUNEYT
 */
public class ReaderListModel implements ListModel {

    private List<Reader> collection;

    public ReaderListModel(Reader[] collection) {
        this.collection = new ArrayList<Reader>();
        this.collection.addAll(Arrays.asList(collection));
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

    public List<Reader> getCollection() {
        return collection;
    }

    public void setCollection(List<Reader> collection) {
        this.collection = collection;
    }
}
