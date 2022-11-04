/*
 * Created by JFormDesigner on Thu Nov 03 14:44:42 CST 2022
 */

package com.cylmms.view;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.cylmms.pojo.Gp;
import com.cylmms.pojo.User;
import com.cylmms.service.GpService;
import com.cylmms.service.UserService;
import com.cylmms.utils.EncryptUtils;
import com.cylmms.vo.GpVo;
import com.cylmms.vo.UserVo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        JTableHeader gpTableHeader = gpTable.getTableHeader();
        JTableHeader addGpTableTableHeader = addGpTable.getTableHeader();
        userTableHeader.setFont(userTableHeader.getFont().deriveFont(userTableHeader.getFont().getSize() + 5f));
        addUserTableHeader.setFont(addUserTableHeader.getFont().deriveFont(addUserTableHeader.getFont().getSize() + 5f));
        gpTableHeader.setFont(gpTableHeader.getFont().deriveFont(gpTableHeader.getFont().getSize() + 5f));
        addGpTableTableHeader.setFont(addGpTableTableHeader.getFont().deriveFont(addGpTableTableHeader.getFont().getSize() + 5f));
    }

    private void freshUserDate() {
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

    private void freshGpDate() {
        GpVo gpVo = new GpVo();
        String name = gpNameField.getText();
        String supGp = supGpField.getText();
        String category = categoryField.getText();
        String industry = industryField.getText();
        gpVo.setName(name);
        gpVo.setSuperior(supGp);
        gpVo.setCategory(category);
        gpVo.setIndustry(industry);
        List<Gp> gpList = GpService.getByCondition(gpVo);
        Object[][] gps = new Object[gpList.size()][5];
        for (int i = 0; i < gpList.size(); i++) {
            Gp gp = gpList.get(i);
            gps[i][0] = gp.getName();
            String superior = gp.getSuperior();
            if (superior == null) {
                gps[i][1] = "";
            } else {
                gps[i][1] = superior;
            }
            gps[i][2] = gp.getCategory();
            gps[i][3] = gp.getIndustry();
            gps[i][4] = gp.getMemNum();
        }
        gpTable.setModel(new DefaultTableModel(
                gps,
                new String[]{
                        "\u7ec4\u7ec7\u540d\u79f0", "\u4e0a\u7ea7\u7ec4\u7ec7", "\u7ec4\u7ec7\u7c7b\u522b", "\u6240\u5c5e\u884c\u4e1a", "\u4eba\u6570"
                }
        ) {
            Class<?>[] columnTypes = new Class<?>[]{
                    String.class, String.class, String.class, String.class, Integer.class
            };
            boolean[] columnEditable = new boolean[]{
                    false, true, true, true, false
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
        freshUserDate();
    }

    private void customInitComponents() {
        setTableHeaderFont();
    }

    private User getUserData(TableModel model, int rowIndex) {
        User user = new User();
        String idCard = (String) model.getValueAt(rowIndex, 0);
        user.setIdCard(idCard);
        String name = (String) model.getValueAt(rowIndex, 1);
        user.setName(name);
        String password = (String) model.getValueAt(rowIndex, 2);
        user.setPassword(password);
        String duty = (String) model.getValueAt(rowIndex, 3);
        user.setDuty(duty);
        user.setSuperAdmin(0);
        return user;
    }

    private Gp getGpData(TableModel model, int rowIndex) {
        Gp gp = new Gp();
        String name = (String) model.getValueAt(rowIndex, 0);
        gp.setName(name);
        String sup = (String) model.getValueAt(rowIndex, 1);
        if ("".equals(sup)) {
            gp.setSuperior(null);
        } else {
            gp.setSuperior(sup);
        }
        String category = (String) model.getValueAt(rowIndex, 2);
        gp.setCategory(category);
        String industry = (String) model.getValueAt(rowIndex, 3);
        gp.setIndustry(industry);
        gp.setMemNum(0);
        return gp;
    }

    private void updateUserDate(ActionEvent e) {
        int[] selectedRows = userTable.getSelectedRows();
        TableModel model = userTable.getModel();
        ArrayList<User> userList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            userList.add(getUserData(model, rowIndex));
        }
        try {
            UserService.batchUpdateUser(userList);
            freshUserDate();
        } catch (Exception ex) {
            freshUserDate();
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
            freshUserDate();
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

    private void selectGp(ActionEvent e) {
        freshGpDate();
    }

    private void updateGpDate(ActionEvent e) {
        int[] selectedRows = gpTable.getSelectedRows();
        TableModel model = gpTable.getModel();
        ArrayList<Gp> gpList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            gpList.add(getGpData(model, rowIndex));
        }
        try {
            GpService.batchUpdateGp(gpList);
            freshGpDate();
        } catch (Exception ex) {
            freshGpDate();
            new ErrorDialog(new JFrame()).error(ex.getMessage());
        }
    }

    private void deleteGp(ActionEvent e) {
        int[] selectedRows = gpTable.getSelectedRows();
        TableModel model = gpTable.getModel();
        ArrayList<String> nameList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            String name = (String) model.getValueAt(rowIndex, 0);
            nameList.add(name);
        }
        try {
            GpService.batchDeleteGp(nameList);
            freshGpDate();
        } catch (Exception ex) {
            freshGpDate();
            new ErrorDialog(new JFrame()).error(ex.getMessage());
        }
    }

    private void addGpSpace(ActionEvent e) {
        String num = addGpNumField.getText();
        if (!StrUtil.isEmpty(num)) {
            Integer anInt;
            if (NumberUtil.isNumber(num)) {
                anInt = Integer.valueOf(num);
            } else {
                new ErrorDialog(new JFrame()).error("添加空行数输入错误！");
                return;
            }
            Object[][] table = new Object[anInt][4];
            addGpTable.setModel(new DefaultTableModel(
                    table,
                    new String[]{
                            "\u540d\u79f0", "\u4e0a\u7ea7\u7ec4\u7ec7", "\u7ec4\u7ec7\u7c7b\u522b", "\u6240\u5c5e\u884c\u4e1a"
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

    private void addGp(ActionEvent e) {
        TableModel model = addGpTable.getModel();
        int rowCount = addGpTable.getRowCount();
        ArrayList<Gp> gpList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Gp gp = getGpData(model, i);
            gpList.add(gp);
        }
        try {
            GpService.batchAddGp(gpList);
            addGpTable.setModel(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                            "\u540d\u79f0", "\u4e0a\u7ea7\u7ec4\u7ec7", "\u7ec4\u7ec7\u7c7b\u522b", "\u6240\u5c5e\u884c\u4e1a"
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
            addGpNumField.setText("");
        } catch (Exception ex) {
            new ErrorDialog(new JFrame()).error(ex.getMessage());
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        tabbedPane = new JTabbedPane();
        userSelectAndUpdateAndDeletePanel = new JPanel();
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
        GpSelectAndUpdateAndDeletePanel = new JPanel();
        scrollPane3 = new JScrollPane();
        gpTable = new JTable();
        vSpacer4 = new JPanel(null);
        vSpacer5 = new JPanel(null);
        hSpacer3 = new JPanel(null);
        hSpacer4 = new JPanel(null);
        gpNameLabel = new JLabel();
        gpNameField = new JTextField();
        supGpLabel = new JLabel();
        supGpField = new JTextField();
        categoryLabel = new JLabel();
        categoryField = new JTextField();
        industryLabel = new JLabel();
        industryField = new JTextField();
        selectGpButton = new JButton();
        updateGpButton = new JButton();
        deleteGpButton = new JButton();
        vSpacer6 = new JPanel(null);
        gpAddPanel = new JPanel();
        scrollPane4 = new JScrollPane();
        addGpTable = new JTable();
        vSpacer7 = new JPanel(null);
        vSpacer8 = new JPanel(null);
        hSpacer5 = new JPanel(null);
        addGpNumLabel = new JLabel();
        addGpNumField = new JTextField();
        vSpacer12 = new JPanel(null);
        hSpacer8 = new JPanel(null);
        addGpSpaceButton = new JButton();
        addGpButton = new JButton();

        //======== this ========
        setTitle("\u56e2\u5458\u7ba1\u7406\u7cfb\u7edf-\u8d85\u7ea7\u7ba1\u7406");
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.jpeg")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();

        //======== tabbedPane ========
        {
            tabbedPane.setFont(tabbedPane.getFont().deriveFont(tabbedPane.getFont().getSize() + 7f));

            //======== userSelectAndUpdateAndDeletePanel ========
            {
                userSelectAndUpdateAndDeletePanel.setFont(userSelectAndUpdateAndDeletePanel.getFont().deriveFont(userSelectAndUpdateAndDeletePanel.getFont().getSize() + 5f));

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
                updateButton.addActionListener(e -> updateUserDate(e));

                //---- deleteButton ----
                deleteButton.setText("\u5220\u9664");
                deleteButton.setFont(deleteButton.getFont().deriveFont(deleteButton.getFont().getSize() + 5f));
                deleteButton.addActionListener(e -> delete(e));

                GroupLayout userSelectAndUpdateAndDeletePanelLayout = new GroupLayout(userSelectAndUpdateAndDeletePanel);
                userSelectAndUpdateAndDeletePanel.setLayout(userSelectAndUpdateAndDeletePanelLayout);
                userSelectAndUpdateAndDeletePanelLayout.setHorizontalGroup(
                        userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(vSpacer2, GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE))
                                                .addComponent(vSpacer1, GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addComponent(hSpacer1, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addComponent(dutyLabel)
                                                                .addComponent(idCardLable, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(12, 12, 12)
                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(idCardField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                                                .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                                                .addComponent(dutyField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(vSpacer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addComponent(updateButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(selectButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(deleteButton, GroupLayout.Alignment.TRAILING))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(hSpacer2, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())
                );
                userSelectAndUpdateAndDeletePanelLayout.setVerticalGroup(
                        userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(idCardField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(idCardLable, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(userSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(dutyLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(dutyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                                .addComponent(vSpacer3, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(userSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
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
            tabbedPane.addTab("\u7ba1\u7406\u5458\u7684\u67e5\u8be2\u66f4\u65b0\u5220\u9664", userSelectAndUpdateAndDeletePanel);

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

            //======== GpSelectAndUpdateAndDeletePanel ========
            {
                GpSelectAndUpdateAndDeletePanel.setFont(GpSelectAndUpdateAndDeletePanel.getFont().deriveFont(GpSelectAndUpdateAndDeletePanel.getFont().getSize() + 5f));

                //======== scrollPane3 ========
                {

                    //---- gpTable ----
                    gpTable.setFont(gpTable.getFont().deriveFont(gpTable.getFont().getSize() + 5f));
                    gpTable.setModel(new DefaultTableModel(
                            new Object[][]{
                            },
                            new String[]{
                                    "\u7ec4\u7ec7\u540d\u79f0", "\u4e0a\u7ea7\u7ec4\u7ec7", "\u7ec4\u7ec7\u7c7b\u522b", "\u6240\u5c5e\u884c\u4e1a", "\u4eba\u6570"
                            }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[]{
                                String.class, String.class, String.class, String.class, Integer.class
                        };
                        boolean[] columnEditable = new boolean[]{
                                false, true, true, true, false
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
                    gpTable.setRowHeight(35);
                    scrollPane3.setViewportView(gpTable);
                }

                //---- gpNameLabel ----
                gpNameLabel.setText("\u7ec4\u7ec7\u540d\u79f0\uff1a");
                gpNameLabel.setFont(gpNameLabel.getFont().deriveFont(gpNameLabel.getFont().getSize() + 5f));

                //---- gpNameField ----
                gpNameField.setFont(gpNameField.getFont().deriveFont(gpNameField.getFont().getSize() + 5f));

                //---- supGpLabel ----
                supGpLabel.setText("\u4e0a\u7ea7\u7ec4\u7ec7\uff1a");
                supGpLabel.setFont(supGpLabel.getFont().deriveFont(supGpLabel.getFont().getSize() + 5f));

                //---- supGpField ----
                supGpField.setFont(supGpField.getFont().deriveFont(supGpField.getFont().getSize() + 5f));

                //---- categoryLabel ----
                categoryLabel.setText("\u7ec4\u7ec7\u7c7b\u522b\uff1a");
                categoryLabel.setFont(categoryLabel.getFont().deriveFont(categoryLabel.getFont().getSize() + 5f));

                //---- categoryField ----
                categoryField.setFont(categoryField.getFont().deriveFont(categoryField.getFont().getSize() + 5f));

                //---- industryLabel ----
                industryLabel.setText("\u6240\u5c5e\u884c\u4e1a\uff1a");
                industryLabel.setFont(industryLabel.getFont().deriveFont(industryLabel.getFont().getSize() + 5f));

                //---- industryField ----
                industryField.setFont(industryField.getFont().deriveFont(industryField.getFont().getSize() + 5f));

                //---- selectGpButton ----
                selectGpButton.setText("\u67e5\u8be2");
                selectGpButton.setFont(selectGpButton.getFont().deriveFont(selectGpButton.getFont().getSize() + 5f));
                selectGpButton.addActionListener(e -> selectGp(e));

                //---- updateGpButton ----
                updateGpButton.setText("\u66f4\u65b0");
                updateGpButton.setFont(updateGpButton.getFont().deriveFont(updateGpButton.getFont().getSize() + 5f));
                updateGpButton.addActionListener(e -> updateGpDate(e));

                //---- deleteGpButton ----
                deleteGpButton.setText("\u5220\u9664");
                deleteGpButton.setFont(deleteGpButton.getFont().deriveFont(deleteGpButton.getFont().getSize() + 5f));
                deleteGpButton.addActionListener(e -> deleteGp(e));

                GroupLayout GpSelectAndUpdateAndDeletePanelLayout = new GroupLayout(GpSelectAndUpdateAndDeletePanel);
                GpSelectAndUpdateAndDeletePanel.setLayout(GpSelectAndUpdateAndDeletePanelLayout);
                GpSelectAndUpdateAndDeletePanelLayout.setHorizontalGroup(
                        GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addComponent(vSpacer4, GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
                                                .addComponent(vSpacer5, GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addComponent(hSpacer3, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(supGpLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(gpNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(gpNameField, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(supGpField, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(categoryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(industryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(categoryField, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                                                .addComponent(industryField, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(vSpacer6, GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
                                                        .addGap(71, 71, 71)
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addComponent(selectGpButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(updateGpButton, GroupLayout.Alignment.TRAILING)
                                                                .addComponent(deleteGpButton, GroupLayout.Alignment.TRAILING))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(hSpacer4, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())
                );
                GpSelectAndUpdateAndDeletePanelLayout.setVerticalGroup(
                        GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                        .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                .addComponent(hSpacer4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup()
                                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(GroupLayout.Alignment.LEADING, GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                                .addComponent(categoryField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(industryField))
                                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(gpNameLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(gpNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(industryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(supGpLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(supGpField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                                                                .addGroup(GpSelectAndUpdateAndDeletePanelLayout.createSequentialGroup()
                                                                        .addComponent(selectGpButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(updateGpButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(deleteGpButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(vSpacer6, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(hSpacer3, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, Short.MAX_VALUE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(vSpacer5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                );
            }
            tabbedPane.addTab("\u56e2\u652f\u90e8\u7684\u67e5\u8be2\u66f4\u65b0\u5220\u9664", GpSelectAndUpdateAndDeletePanel);

            //======== gpAddPanel ========
            {
                gpAddPanel.setFont(gpAddPanel.getFont().deriveFont(gpAddPanel.getFont().getSize() + 5f));

                //======== scrollPane4 ========
                {

                    //---- addGpTable ----
                    addGpTable.setFont(addGpTable.getFont().deriveFont(addGpTable.getFont().getSize() + 5f));
                    addGpTable.setModel(new DefaultTableModel(
                            new Object[][]{
                            },
                            new String[]{
                                    "\u540d\u79f0", "\u4e0a\u7ea7\u7ec4\u7ec7", "\u7ec4\u7ec7\u7c7b\u522b", "\u6240\u5c5e\u884c\u4e1a"
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
                    addGpTable.setRowHeight(35);
                    scrollPane4.setViewportView(addGpTable);
                }

                //---- addGpNumLabel ----
                addGpNumLabel.setText("\u6dfb\u52a0\u4e2a\u6570\uff1a");
                addGpNumLabel.setFont(addGpNumLabel.getFont().deriveFont(addGpNumLabel.getFont().getSize() + 5f));

                //---- addGpNumField ----
                addGpNumField.setFont(addGpNumField.getFont().deriveFont(addGpNumField.getFont().getSize() + 5f));

                //---- addGpSpaceButton ----
                addGpSpaceButton.setText("\u6dfb\u52a0\u7a7a\u884c");
                addGpSpaceButton.setFont(addGpSpaceButton.getFont().deriveFont(addGpSpaceButton.getFont().getSize() + 5f));
                addGpSpaceButton.addActionListener(e -> addGpSpace(e));

                //---- addGpButton ----
                addGpButton.setText("\u6dfb\u52a0\u56e2\u652f\u90e8");
                addGpButton.setFont(addGpButton.getFont().deriveFont(addGpButton.getFont().getSize() + 5f));
                addGpButton.addActionListener(e -> addGp(e));

                GroupLayout gpAddPanelLayout = new GroupLayout(gpAddPanel);
                gpAddPanel.setLayout(gpAddPanelLayout);
                gpAddPanelLayout.setHorizontalGroup(
                        gpAddPanelLayout.createParallelGroup()
                                .addComponent(scrollPane4)
                                .addGroup(gpAddPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(gpAddPanelLayout.createParallelGroup()
                                                .addComponent(vSpacer7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(vSpacer8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(gpAddPanelLayout.createSequentialGroup()
                                                        .addComponent(hSpacer5, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(addGpNumLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(addGpNumField, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(vSpacer12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                                        .addGroup(gpAddPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(addGpButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addGpSpaceButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(hSpacer8, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())
                );
                gpAddPanelLayout.setVerticalGroup(
                        gpAddPanelLayout.createParallelGroup()
                                .addGroup(gpAddPanelLayout.createSequentialGroup()
                                        .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(gpAddPanelLayout.createParallelGroup()
                                                .addGroup(gpAddPanelLayout.createSequentialGroup()
                                                        .addGroup(gpAddPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(addGpNumLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(addGpNumField))
                                                        .addGap(0, 59, Short.MAX_VALUE))
                                                .addComponent(vSpacer12, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                                .addGroup(gpAddPanelLayout.createSequentialGroup()
                                                        .addComponent(addGpSpaceButton)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(addGpButton)
                                                        .addGap(0, 25, Short.MAX_VALUE))
                                                .addComponent(hSpacer8, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                                .addComponent(hSpacer5, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vSpacer8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                );
            }
            tabbedPane.addTab("\u56e2\u652f\u90e8\u7684\u6dfb\u52a0", gpAddPanel);
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
    private JPanel userSelectAndUpdateAndDeletePanel;
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
    private JPanel GpSelectAndUpdateAndDeletePanel;
    private JScrollPane scrollPane3;
    private JTable gpTable;
    private JPanel vSpacer4;
    private JPanel vSpacer5;
    private JPanel hSpacer3;
    private JPanel hSpacer4;
    private JLabel gpNameLabel;
    private JTextField gpNameField;
    private JLabel supGpLabel;
    private JTextField supGpField;
    private JLabel categoryLabel;
    private JTextField categoryField;
    private JLabel industryLabel;
    private JTextField industryField;
    private JButton selectGpButton;
    private JButton updateGpButton;
    private JButton deleteGpButton;
    private JPanel vSpacer6;
    private JPanel gpAddPanel;
    private JScrollPane scrollPane4;
    private JTable addGpTable;
    private JPanel vSpacer7;
    private JPanel vSpacer8;
    private JPanel hSpacer5;
    private JLabel addGpNumLabel;
    private JTextField addGpNumField;
    private JPanel vSpacer12;
    private JPanel hSpacer8;
    private JButton addGpSpaceButton;
    private JButton addGpButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
