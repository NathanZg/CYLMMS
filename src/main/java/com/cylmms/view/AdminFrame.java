/*
 * Created by JFormDesigner on Thu Nov 03 14:44:42 CST 2022
 */

package com.cylmms.view;

import com.cylmms.pojo.User;
import com.cylmms.service.UserService;
import com.cylmms.utils.EncryptUtils;
import com.cylmms.vo.UserVo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ekertree
 */
public class AdminFrame extends JFrame {
    public AdminFrame() {
        initComponents();
        customInitComponents();
    }

    public static void main(String[] args) {
        AdminFrame frame = new AdminFrame();
        frame.setVisible(true);
    }

    public void openView() {
        setVisible(true);
    }

    private void setTableHeaderFont() {
        JTableHeader tableHeader = userTable.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(tableHeader.getFont().getSize() + 5f));
    }

    private void freshDate() {
        UserVo userVo = new UserVo();
        String name = nameField.getText();
        String idCard = idCardField.getText();
        String duty = dutyField.getText();
        userVo.setName(name);
        userVo.setIdCard(idCard);
        userVo.setDuty(duty);
        List<User> userList = UserService.getByCondition(userVo);
        Object[][] users = new Object[userList.size()][4];
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            users[i][0] = user.getIdCard();
            users[i][1] = user.getName();
            users[i][2] = EncryptUtils.decode(user.getPassword());
            users[i][3] = user.getDuty();
        }
        userTable.setModel(new DefaultTableModel(
                users,
                new String[]{
                        "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5bc6\u7801", "\u56e2\u652f\u90e8"
                }
        ) {
            Class<?>[] columnTypes = new Class<?>[]{
                    String.class, String.class, String.class, String.class
            };
            boolean[] columnEditable = new boolean[]{
                    false, true, true, true
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnEditable[columnIndex];
            }
        });
    }

    private void select(ActionEvent e) {
        freshDate();
    }

    private void customInitComponents() {
        setTableHeaderFont();
    }

    private User getUserData(TableModel model, int rowIndex) {
        User user = new User();
        for (int i = 0; i < 4; i++) {
            String idCard = (String) model.getValueAt(rowIndex, 0);
            user.setIdCard(idCard);
            String name = (String) model.getValueAt(rowIndex, 1);
            user.setName(name);
            String password = (String) model.getValueAt(rowIndex, 2);
            user.setPassword(password);
            String duty = (String) model.getValueAt(rowIndex, 3);
            user.setDuty(duty);
            user.setSuperAdmin(0);
        }
        return user;
    }

    private void update(ActionEvent e) {
        int[] selectedRows = userTable.getSelectedRows();
        TableModel model = userTable.getModel();
        ArrayList<User> userList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            userList.add(getUserData(model, rowIndex));
        }
        try {
            UserService.batchUpdateUser(userList);
            freshDate();
        } catch (Exception ex) {
            new ErrorDialog(new JFrame()).error(ex.getMessage());
        }
    }

    private void delete(ActionEvent e) {
        int[] selectedRows = userTable.getSelectedRows();
        TableModel model = userTable.getModel();
        ArrayList<String> idCardList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            String idCard = (String) model.getValueAt(rowIndex, 0);
            idCardList.add(idCard);
        }
        try {
            UserService.batchDeleteMember(idCardList);
            freshDate();
        } catch (Exception ex) {
            new ErrorDialog(new JFrame()).error(ex.getMessage());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        tabbedPane = new JTabbedPane();
        selectAndUpdateAndDeletePanel = new JPanel();
        scrollPane1 = new JScrollPane();
        userTable = new JTable();
        vSpacer1 = new JPanel(null);
        vSpacer2 = new JPanel(null);
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        idCardLable = new JLabel();
        idCardField = new JTextField();
        vSpacer3 = new JPanel(null);
        nameLabel = new JLabel();
        nameField = new JTextField();
        dutyLabel = new JLabel();
        dutyField = new JTextField();
        selectButton = new JButton();
        updateButton = new JButton();
        deleteButton = new JButton();

        //======== this ========
        setTitle("\u56e2\u5458\u7ba1\u7406\u7cfb\u7edf-\u8d85\u7ea7\u7ba1\u7406");
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.jpeg")).getImage());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();

        //======== tabbedPane ========
        {
            tabbedPane.setFont(tabbedPane.getFont().deriveFont(tabbedPane.getFont().getSize() + 7f));

            //======== selectAndUpdateAndDeletePanel ========
            {
                selectAndUpdateAndDeletePanel.setFont(selectAndUpdateAndDeletePanel.getFont().deriveFont(selectAndUpdateAndDeletePanel.getFont().getSize() + 5f));

                //======== scrollPane1 ========
                {

                    //---- userTable ----
                    userTable.setAutoCreateRowSorter(true);
                    userTable.setFont(userTable.getFont().deriveFont(userTable.getFont().getSize() + 5f));
                    userTable.setModel(new DefaultTableModel(
                            new Object[][]{
                            },
                            new String[]{
                                    "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5bc6\u7801", "\u56e2\u652f\u90e8"
                            }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[]{
                                String.class, String.class, String.class, String.class
                        };
                        boolean[] columnEditable = new boolean[]{
                                false, true, true, true
                        };

                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            return columnTypes[columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    userTable.setShowHorizontalLines(true);
                    userTable.setShowVerticalLines(true);
                    userTable.setRowHeight(35);
                    userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    scrollPane1.setViewportView(userTable);
                }

                //---- idCardLable ----
                idCardLable.setText("\u8eab\u4efd\u8bc1\uff1a");
                idCardLable.setFont(idCardLable.getFont().deriveFont(idCardLable.getFont().getSize() + 5f));

                //---- idCardField ----
                idCardField.setFont(idCardField.getFont().deriveFont(idCardField.getFont().getSize() + 5f));

                //---- nameLabel ----
                nameLabel.setText("\u59d3\u540d\uff1a");
                nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getSize() + 5f));

                //---- nameField ----
                nameField.setFont(nameField.getFont().deriveFont(nameField.getFont().getSize() + 5f));

                //---- dutyLabel ----
                dutyLabel.setText("\u56e2\u652f\u90e8\uff1a");
                dutyLabel.setFont(dutyLabel.getFont().deriveFont(dutyLabel.getFont().getSize() + 5f));

                //---- dutyField ----
                dutyField.setFont(dutyField.getFont().deriveFont(dutyField.getFont().getSize() + 5f));

                //---- selectButton ----
                selectButton.setText("\u67e5\u8be2");
                selectButton.setFont(selectButton.getFont().deriveFont(selectButton.getFont().getSize() + 5f));
                selectButton.addActionListener(e -> select(e));

                //---- updateButton ----
                updateButton.setText("\u66f4\u65b0");
                updateButton.setFont(updateButton.getFont().deriveFont(updateButton.getFont().getSize() + 5f));
                updateButton.addActionListener(e -> update(e));

                //---- deleteButton ----
                deleteButton.setText("\u5220\u9664");
                deleteButton.setFont(deleteButton.getFont().deriveFont(deleteButton.getFont().getSize() + 5f));
                deleteButton.addActionListener(e -> delete(e));

                GroupLayout selectAndUpdateAndDeletePanelLayout = new GroupLayout(selectAndUpdateAndDeletePanel);
                selectAndUpdateAndDeletePanel.setLayout(selectAndUpdateAndDeletePanelLayout);
                selectAndUpdateAndDeletePanelLayout.setHorizontalGroup(
                        selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(vSpacer2, GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE))
                                                .addComponent(vSpacer1, GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addComponent(hSpacer1, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addComponent(dutyLabel)
                                                                .addComponent(idCardLable, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(12, 12, 12)
                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(idCardField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                                                .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                                                .addComponent(dutyField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(vSpacer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addComponent(updateButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(selectButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(deleteButton, GroupLayout.Alignment.TRAILING))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(hSpacer2, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())
                );
                selectAndUpdateAndDeletePanelLayout.setVerticalGroup(
                        selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(idCardField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(idCardLable, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(selectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(dutyLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(dutyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                                .addComponent(vSpacer3, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(selectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                        .addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(hSpacer2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(hSpacer1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                );
            }
            tabbedPane.addTab("\u7ba1\u7406\u5458\u7684\u67e5\u8be2\u66f4\u65b0\u4fee\u6539", selectAndUpdateAndDeletePanel);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTabbedPane tabbedPane;
    private JPanel selectAndUpdateAndDeletePanel;
    private JScrollPane scrollPane1;
    private JTable userTable;
    private JPanel vSpacer1;
    private JPanel vSpacer2;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JLabel idCardLable;
    private JTextField idCardField;
    private JPanel vSpacer3;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel dutyLabel;
    private JTextField dutyField;
    private JButton selectButton;
    private JButton updateButton;
    private JButton deleteButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
