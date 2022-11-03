/*
 * Created by JFormDesigner on Thu Nov 03 14:44:42 CST 2022
 */

package com.cylmms.view;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
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
        JTableHeader userTableHeader = userTable.getTableHeader();
        JTableHeader addUserTableHeader = addUserTable.getTableHeader();
        userTableHeader.setFont(userTableHeader.getFont().deriveFont(userTableHeader.getFont().getSize() + 5f));
        addUserTableHeader.setFont(addUserTableHeader.getFont().deriveFont(addUserTableHeader.getFont().getSize() + 5f));
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

    private void addSpace(ActionEvent e) {
        String num = addNumField.getText();
        if (!StrUtil.isEmpty(num)) {
            Integer anInt;
            if (NumberUtil.isNumber(num)) {
                anInt = Integer.valueOf(num);
            } else {
                new ErrorDialog(new JFrame()).error("添加空行数输入错误！");
                return;
            }
            Object[][] table = new Object[anInt][4];
            addUserTable.setModel(new DefaultTableModel(
                    table,
                    new String[]{
                            "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5bc6\u7801", "\u56e2\u652f\u90e8"
                    }
            ) {
                Class<?>[] columnTypes = new Class<?>[]{
                        String.class, String.class, String.class, String.class
                };

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });
        }
    }

    private void addUser(ActionEvent e) {
        TableModel model = addUserTable.getModel();
        int rowCount = addUserTable.getRowCount();
        ArrayList<User> userList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            User user = getUserData(model, i);
            userList.add(user);
        }
        try {
            UserService.batchAddUser(userList);
            addUserTable.setModel(new DefaultTableModel(
                    new Object[][]{
                    },
                    new String[]{
                            "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5bc6\u7801", "\u56e2\u652f\u90e8"
                    }
            ) {
                Class<?>[] columnTypes = new Class<?>[]{
                        String.class, String.class, String.class, String.class
                };

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });
            addNumField.setText("");
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
        addPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        addUserTable = new JTable();
        vSpacer9 = new JPanel(null);
        vSpacer10 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        hSpacer7 = new JPanel(null);
        addNumLabel = new JLabel();
        addNumField = new JTextField();
        vSpacer11 = new JPanel(null);
        addSpaceButton = new JButton();
        addUserButton = new JButton();

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

            //======== addPanel ========
            {
                addPanel.setFont(addPanel.getFont().deriveFont(addPanel.getFont().getSize() + 5f));

                //======== scrollPane2 ========
                {

                    //---- addUserTable ----
                    addUserTable.setModel(new DefaultTableModel(
                            new Object[][]{
                            },
                            new String[]{
                                    "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5bc6\u7801", "\u56e2\u652f\u90e8"
                            }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[]{
                                String.class, String.class, String.class, String.class
                        };

                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            return columnTypes[columnIndex];
                        }
                    });
                    addUserTable.setAutoCreateRowSorter(true);
                    addUserTable.setRowHeight(35);
                    addUserTable.setFont(addUserTable.getFont().deriveFont(addUserTable.getFont().getSize() + 5f));
                    scrollPane2.setViewportView(addUserTable);
                }

                //---- addNumLabel ----
                addNumLabel.setText("\u6dfb\u52a0\u4eba\u6570\uff1a");
                addNumLabel.setFont(addNumLabel.getFont().deriveFont(addNumLabel.getFont().getSize() + 5f));

                //---- addNumField ----
                addNumField.setFont(addNumField.getFont().deriveFont(addNumField.getFont().getSize() + 5f));

                //---- addSpaceButton ----
                addSpaceButton.setText("\u6dfb\u52a0\u7a7a\u884c");
                addSpaceButton.setFont(addSpaceButton.getFont().deriveFont(addSpaceButton.getFont().getSize() + 5f));
                addSpaceButton.addActionListener(e -> addSpace(e));

                //---- addUserButton ----
                addUserButton.setText("\u6dfb\u52a0\u7ba1\u7406\u5458");
                addUserButton.setFont(addUserButton.getFont().deriveFont(addUserButton.getFont().getSize() + 5f));
                addUserButton.addActionListener(e -> addUser(e));

                GroupLayout addPanelLayout = new GroupLayout(addPanel);
                addPanel.setLayout(addPanelLayout);
                addPanelLayout.setHorizontalGroup(
                        addPanelLayout.createParallelGroup()
                                .addComponent(scrollPane2)
                                .addGroup(addPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(addPanelLayout.createParallelGroup()
                                                .addComponent(vSpacer9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(vSpacer10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(addPanelLayout.createSequentialGroup()
                                                        .addComponent(hSpacer6, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(addNumLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(addNumField, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(vSpacer11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(addSpaceButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(hSpacer7, GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())
                );
                addPanelLayout.setVerticalGroup(
                        addPanelLayout.createParallelGroup()
                                .addGroup(addPanelLayout.createSequentialGroup()
                                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(addPanelLayout.createParallelGroup()
                                                .addComponent(hSpacer7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(addPanelLayout.createSequentialGroup()
                                                        .addGroup(addPanelLayout.createParallelGroup()
                                                                .addGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(addNumField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(addNumLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(addPanelLayout.createSequentialGroup()
                                                                        .addComponent(addSpaceButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(addUserButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(hSpacer6, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(vSpacer11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                );
            }
            tabbedPane.addTab("\u7ba1\u7406\u5458\u7684\u6dfb\u52a0", addPanel);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
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
    private JPanel addPanel;
    private JScrollPane scrollPane2;
    private JTable addUserTable;
    private JPanel vSpacer9;
    private JPanel vSpacer10;
    private JPanel hSpacer6;
    private JPanel hSpacer7;
    private JLabel addNumLabel;
    private JTextField addNumField;
    private JPanel vSpacer11;
    private JButton addSpaceButton;
    private JButton addUserButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
