/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdminFrame.java
 *
 * Created on 25-Mar-2011, 03:31:06
 */
package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import server.Configure;
import server.SLogger;
import server.SubscriptionServer;

/**
 *
 * @author CUNEYT
 */
public class AdminFrame extends javax.swing.JFrame implements Observer {

    private SubscriptionServer server;

    /** Creates new form AdminFrame */
    public AdminFrame() {
        initComponents();
        init();
    }

    private void init() {
        try {
            Configure config = new Configure();
            int serverPort = Integer.parseInt(config.getProperty("serverPort"));
            String username = config.getProperty("dbUsername");
            String password = config.getProperty("dbPassword");
            String host = config.getProperty("dbURL");
            server = new SubscriptionServer(serverPort, username, password, host);
            server.addObserver(this);
            readersList.setModel(new ReaderListModel(server.getReaderList()));
            setLocationRelativeTo(null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Konfigürasyon dosyasına erişilemiyor!");
            SLogger.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new AdminFrame().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AdminPanel = new javax.swing.JPanel();
        messageScrollPane = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        categoryLabel = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox();
        readersScrollPane = new javax.swing.JScrollPane();
        readersList = new javax.swing.JList();
        readersLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        clock1 = new view.Clock();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administration Panel");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        messageArea.setColumns(20);
        messageArea.setRows(5);
        messageScrollPane.setViewportView(messageArea);

        categoryLabel.setText("Kategori");

        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sinyaller", "Anlık Yorumlar" }));

        readersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        readersScrollPane.setViewportView(readersList);

        readersLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/users.png"))); // NOI18N
        readersLabel.setText("BAGLI KULLANICILAR");

        messageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/mail.png"))); // NOI18N
        messageLabel.setText("Mesaj");

        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/send.png"))); // NOI18N
        sendButton.setText("GÖNDER");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        resetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/delete.png"))); // NOI18N
        resetButton.setText("TEMIZLE");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminPanelLayout = new javax.swing.GroupLayout(AdminPanel);
        AdminPanel.setLayout(AdminPanelLayout);
        AdminPanelLayout.setHorizontalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanelLayout.createSequentialGroup()
                        .addComponent(categoryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(categoryComboBox, 0, 406, Short.MAX_VALUE))
                    .addComponent(messageScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanelLayout.createSequentialGroup()
                        .addComponent(resetButton)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton)))
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AdminPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(readersScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(readersLabel)))
                    .addGroup(AdminPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clock1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AdminPanelLayout.setVerticalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(categoryLabel))
                    .addComponent(clock1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageLabel)
                    .addComponent(readersLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanelLayout.createSequentialGroup()
                        .addComponent(messageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendButton)
                            .addComponent(resetButton)))
                    .addComponent(readersScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AdminPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AdminPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        messageArea.setText("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed

        if (messageArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mesaj alanı boş!");
        } else {
            server.broadcastMessage(messageArea.getText(), categoryComboBox.getSelectedIndex() + 1);
            messageArea.setText("");
        }

    }//GEN-LAST:event_sendButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
server.unpublishIPOnDB();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminPanel;
    private javax.swing.JComboBox categoryComboBox;
    private javax.swing.JLabel categoryLabel;
    private view.Clock clock1;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JScrollPane messageScrollPane;
    private javax.swing.JLabel readersLabel;
    private javax.swing.JList readersList;
    private javax.swing.JScrollPane readersScrollPane;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object arg) {
        readersList.setModel(new ReaderListModel(server.getReaderList()));
    }
}
