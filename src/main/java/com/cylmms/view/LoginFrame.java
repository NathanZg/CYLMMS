/*
 * Created by JFormDesigner on Mon Oct 31 21:57:15 CST 2022
 */

package com.cylmms.view;

import com.cylmms.pojo.User;
import com.cylmms.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author ekertree
 */
public class LoginFrame {
    public LoginFrame() {
        initComponents();
    }

    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
        frame.openView();
    }

    public void openView() {
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void closeView() {
        loginFrame.dispose();
    }

    private void login(ActionEvent e) {
        String idCard = accountField.getText();
        String password = new String(passwordField.getPassword());
        User user = new User(idCard, password);
        if (UserService.login(user)) {
            closeView();
            MainFrame mainFrame = new MainFrame();
            mainFrame.openView();
        }
    }

    private void exit(ActionEvent e) {
        closeView();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        loginFrame = new JFrame();
        layeredPane = new JLayeredPane();
        contentPanel = new JPanel();
        acountLabel = new JLabel();
        accountField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        loginButton = new JButton();
        exitButton = new JButton();
        background = new JLabel();

        //======== loginFrame ========
        {
            loginFrame.setIconImage(null);
            loginFrame.setTitle("\u56e2\u5458\u7ba1\u7406\u7cfb\u7edf-\u767b\u9646");
            Container loginFrameContentPane = loginFrame.getContentPane();

            //======== layeredPane ========
            {

                //======== contentPanel ========
                {
                    contentPanel.setOpaque(false);

                    //---- acountLabel ----
                    acountLabel.setText("\u8d26\u53f7\uff1a");
                    acountLabel.setFont(acountLabel.getFont().deriveFont(acountLabel.getFont().getSize() + 15f));

                    //---- passwordLabel ----
                    passwordLabel.setText("\u5bc6\u7801\uff1a");
                    passwordLabel.setFont(passwordLabel.getFont().deriveFont(passwordLabel.getFont().getSize() + 15f));

                    //---- loginButton ----
                    loginButton.setText("\u767b\u9646");
                    loginButton.setFont(loginButton.getFont().deriveFont(loginButton.getFont().getSize() + 15f));
                    loginButton.addActionListener(e -> login(e));

                    //---- exitButton ----
                    exitButton.setText("\u9000\u51fa");
                    exitButton.setFont(exitButton.getFont().deriveFont(exitButton.getFont().getSize() + 15f));
                    exitButton.addActionListener(e -> exit(e));

                    GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                    contentPanel.setLayout(contentPanelLayout);
                    contentPanelLayout.setHorizontalGroup(
                            contentPanelLayout.createParallelGroup()
                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addGap(160, 160, 160)
                                            .addGroup(contentPanelLayout.createParallelGroup()
                                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                                            .addComponent(passwordLabel)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                                            .addComponent(acountLabel)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(accountField, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                                            .addGap(75, 75, 75)
                                                            .addComponent(loginButton)
                                                            .addGap(65, 65, 65)
                                                            .addComponent(exitButton)))
                                            .addContainerGap(199, Short.MAX_VALUE))
                    );
                    contentPanelLayout.setVerticalGroup(
                            contentPanelLayout.createParallelGroup()
                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addGap(53, 53, 53)
                                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(accountField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(acountLabel))
                                            .addGap(58, 58, 58)
                                            .addGroup(contentPanelLayout.createParallelGroup()
                                                    .addComponent(passwordLabel)
                                                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                            .addGap(100, 100, 100)
                                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(loginButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addContainerGap(159, Short.MAX_VALUE))
                    );
                }
                layeredPane.add(contentPanel, JLayeredPane.PALETTE_LAYER);
                contentPanel.setBounds(new Rectangle(new Point(5, 0), contentPanel.getPreferredSize()));

                //---- background ----
                background.setIcon(new ImageIcon(getClass().getResource("/img/background.jpg")));
                background.setHorizontalAlignment(SwingConstants.CENTER);
                layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
                background.setBounds(0, 0, 725, 485);
            }

            GroupLayout loginFrameContentPaneLayout = new GroupLayout(loginFrameContentPane);
            loginFrameContentPane.setLayout(loginFrameContentPaneLayout);
            loginFrameContentPaneLayout.setHorizontalGroup(
                    loginFrameContentPaneLayout.createParallelGroup()
                            .addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
            );
            loginFrameContentPaneLayout.setVerticalGroup(
                    loginFrameContentPaneLayout.createParallelGroup()
                            .addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
            );
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(loginFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame loginFrame;
    private JLayeredPane layeredPane;
    private JPanel contentPanel;
    private JLabel acountLabel;
    private JTextField accountField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
