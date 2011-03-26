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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import model.Message;

/**
 *
 * @author CUNEYT
 */
public class ClientFrame extends javax.swing.JFrame implements Observer {

    private Client client;
    private JavaSoundAudioClip sound;
    private int selected = Message.SIGNAL;
    private String username;
    private String password;

    /** Creates new form ClientFrame */
    public ClientFrame(String username, String password) throws NullPointerException {
        initComponents();
        this.username = username;
        this.password = password;
        init();
    }

    private void init() throws NullPointerException {
        try {
            client = new Client(username, password, this);
            client.addObserver(this);
            client.start();
            sound = new JavaSoundAudioClip(new FileInputStream(new File("resources/newMessage.wav")));
            messageList.addMouseListener(new ActionJList(messageList));
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
        reconnectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        playSoundCheckBox.setText("Sesli Uyarı");

        messageList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        messageList.setCellRenderer(new MessageCellRenderer());
        messagesScrollPane.setViewportView(messageList);

        viewSignalsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/signal.png"))); // NOI18N
        viewSignalsButton.setText("Sinyaller");
        viewSignalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSignalsButtonActionPerformed(evt);
            }
        });

        commentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/comment.png"))); // NOI18N
        commentsButton.setText("Yorumlar");
        commentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButtonActionPerformed(evt);
            }
        });

        reconnectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/socket.png"))); // NOI18N
        reconnectButton.setText("Bağlantı Yenile");
        reconnectButton.setEnabled(false);
        reconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectButtonActionPerformed(evt);
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
                        .addComponent(commentsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(reconnectButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewSignalsButton)
                    .addComponent(commentsButton)
                    .addComponent(reconnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
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

    private void reconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectButtonActionPerformed
        client.connect();
    }//GEN-LAST:event_reconnectButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        client.finish();
    }//GEN-LAST:event_formWindowClosing

    private void updateMessageList() {
        if (selected == Message.SIGNAL) {
            messageList.setModel(new MessageModel(client.getSignals()));
        } else {
            messageList.setModel(new MessageModel(client.getComments()));
        }
        messageList.ensureIndexIsVisible(messageList.getModel().getSize() - 1);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton commentsButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JList messageList;
    private javax.swing.JScrollPane messagesScrollPane;
    private javax.swing.JCheckBox playSoundCheckBox;
    private javax.swing.JButton reconnectButton;
    private javax.swing.JButton viewSignalsButton;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object arg) {
        if (playSoundCheckBox.isSelected()) {
            sound.play();
        }
        updateMessageList();
    }

    class ActionJList extends MouseAdapter {

        protected JList list;

        public ActionJList(JList l) {
            list = l;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Message msg = (Message) list.getSelectedValue();
                JOptionPane.showMessageDialog(null, msg.getContent(), msg.getDate() + " " + msg.getTime(), JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public void setConnectionButtonVisible(boolean aFlag) {
        reconnectButton.setEnabled(aFlag);
    }
}
