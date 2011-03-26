/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import model.Message;

/**
 *
 * @author CUNEYT
 */
public class MessageListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel c = (JLabel) super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);

        if (value instanceof Message) {

            if (isSelected) {
                c.setBackground(Color.blue);
                c.setForeground(Color.white);
            } else {

                if (index % 2 == 0) {
                    c.setBackground(new Color(204, 204, 204));
                } else {
                    c.setBackground(Color.white);
                }
                
            }
            c.setFont(new Font("Roman", Font.PLAIN, 12));
            c.setText(((Message) value).getContent());
        }
        return c;
    }
}
