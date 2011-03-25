/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClientFrame.java
 *
 * Created on 25-Mar-2011, 20:01:06
 */
package view;

import client.Client;
import com.sun.media.sound.JavaSoundAudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Message;

/**
 *
 * @author CUNEYT
 */
public class ClientFrame extends javax.swing.JFrame implements Observer {

    private Client client;
    private JavaSoundAudioClip sound;
    private int selected = Message.SIGNAL;

    /** Creates new form ClientFrame */
    public ClientFrame() {
        initComponents();
        init();
    }

    private void init() {
        try {
            client = new Client();
            client.addObserver(this);
            client.start();
            sound = new JavaSoundAudioClip(new FileInputStream(new File("resources/newMessage.wav")));
            updateMessageList();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        playSoundCheckBox = new javax.swing.JCheckBox();
        messagesScrollPane = new javax.swing.JScrollPane();
        messageList = new javax.swing.JList();
        viewSignalsButton = new javax.swing.JButton();
        commentsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        playSoundCheckBox.setText("Sesli Uyarı");

        messageList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        messageList.setCellRenderer(new MessageCellRenderer());
        messagesScrollPane.setViewportView(messageList);

        viewSignalsButton.setText("Sinyaller");
        viewSignalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSignalsButtonActionPerformed(evt);
            }
        });

        commentsButton.setText("Yorumlar");
        commentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messagesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                    .addComponent(playSoundCheckBox)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(viewSignalsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentsButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewSignalsButton)
                    .addComponent(commentsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playSoundCheckBox)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void viewSignalsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSignalsButtonActionPerformed
        selected = Message.SIGNAL;
        updateMessageList();
    }//GEN-LAST:event_viewSignalsButtonActionPerformed

    private void commentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButtonActionPerformed
        selected = Message.COMMENT;
        updateMessageList();
    }//GEN-LAST:event_commentsButtonActionPerformed

    private void updateMessageList() {
        if (selected == Message.SIGNAL) {
            messageList.setModel(new MessageModel(client.getSignals()));
        } else {
            messageList.setModel(new MessageModel(client.getComments()));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new ClientFrame().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton commentsButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JList messageList;
    private javax.swing.JScrollPane messagesScrollPane;
    private javax.swing.JCheckBox playSoundCheckBox;
    private javax.swing.JButton viewSignalsButton;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object arg) {
        if (playSoundCheckBox.isSelected()) {
            sound.play();
        }
        updateMessageList();
    }
}