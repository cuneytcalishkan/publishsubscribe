/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import model.Message;

/**
 *
 * @author CUNEYT
 */
public class MessageCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
        if (value instanceof Message) {
            label.setHorizontalTextPosition(JLabel.LEFT);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setText(((Message) value).toString());
        }

        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
