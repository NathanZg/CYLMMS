/*
 * Created by JFormDesigner on Mon Oct 31 17:29:05 CST 2022
 */

package com.cylmms.view;

import cn.hutool.core.util.StrUtil;
import com.cylmms.pojo.Gp;
import com.cylmms.pojo.Member;
import com.cylmms.pojo.User;
import com.cylmms.service.GpService;
import com.cylmms.service.MemberService;
import com.cylmms.vo.MemberVo;

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
public class MainFrame {
    public MainFrame() {
        initComponents();
        customInitComponents();
    }

    public MainFrame(User user) {
        loginUser = user;
        initComponents();
        customInitComponents();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.openView();
    }

    public void openView() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void select(ActionEvent e) {
        freshDate();
    }

    private void freshDate() {
        MemberVo memberVo = new MemberVo();
        String minAge = minAgeField.getText();
        String maxAge = maxAgeField.getText();
        String name = nameField.getText();
        String idCard = idCardField.getText();
        String politicsStatus = politicsStatusField.getText();
        if (!StrUtil.isEmpty(minAge)) {
            memberVo.setMinAge(Integer.parseInt(minAge));
        }
        if (!StrUtil.isEmpty(maxAge)) {
            memberVo.setMaxAge(Integer.parseInt(maxAge));
        }
        memberVo.setName(name);
        memberVo.setIdCard(idCard);
        memberVo.setPoliticsStatus(politicsStatus);
        memberVo.setAffiliated(loginUser.getDuty());
        List<Member> memberList = MemberService.getByCondition(memberVo);
        Object[][] members = new Object[memberList.size()][8];
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            members[i][0] = member.getIdCard();
            members[i][1] = member.getName();
            members[i][2] = member.getAge();
            members[i][3] = member.getGroupAge();
            members[i][4] = member.getPoliticsStatus();
            members[i][5] = member.getNational();
            members[i][6] = member.getDuty();
            members[i][7] = member.getAffiliated();
        }
        memberTable.setModel(new DefaultTableModel(
                members,
                new String[]{
                        "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5e74\u9f84", "\u56e2\u9f84", "\u653f\u6cbb\u9762\u8c8c", "\u6c11\u65cf", "\u56e2\u5185\u804c\u52a1", "\u6240\u5c5e\u56e2\u652f\u90e8"
                }
        ) {
            Class<?>[] columnTypes = new Class<?>[]{
                    String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class
            };
            boolean[] columnEditable = new boolean[]{
                    false, true, true, true, true, true, true, false
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

    private void setTableHeaderFont() {
        JTableHeader memberTableHeader = memberTable.getTableHeader();
        JTableHeader addTableHeader = addMemberTable.getTableHeader();
        memberTableHeader.setFont(memberTableHeader.getFont().deriveFont(memberTableHeader.getFont().getSize() + 5f));
        addTableHeader.setFont(addTableHeader.getFont().deriveFont(addTableHeader.getFont().getSize() + 5f));
    }

    private void customInitComponents() {
        setTableHeaderFont();
        setIndexInfo();
    }

    private void setIndexInfo() {
        welcomeLabel.setText("欢迎您，" + loginUser.getName() + "!");
        Gp g = getGp();
        curGpLabel.setText("当前团支部：" + g.getName());
        supGpLabel.setText("所属上级团支部：" + g.getSuperior());
        categoryLabel.setText("组织类别：" + g.getCategory());
        industryLabel.setText("所属行业：" + g.getIndustry());
        memberNumLabel.setText("团员人数：" + g.getMemNum());
    }

    private Gp getGp() {
        gp = GpService.getGp(loginUser.getDuty());
        return gp;
    }

    private void update(ActionEvent e) {
        int[] selectedRows = memberTable.getSelectedRows();
        TableModel model = memberTable.getModel();
        ArrayList<Member> memberList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            memberList.add(getMemberData(model, rowIndex));
        }
        try {
            MemberService.batchUpdateMember(memberList);
            freshDate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Member getMemberData(TableModel model, int rowIndex) {
        Member member = new Member();
        for (int i = 0; i < 8; i++) {
            String idCard = (String) model.getValueAt(rowIndex, 0);
            member.setIdCard(idCard);
            String name = (String) model.getValueAt(rowIndex, 1);
            member.setName(name);
            Integer age = (Integer) model.getValueAt(rowIndex, 2);
            member.setAge(age);
            Integer groupAge = (Integer) model.getValueAt(rowIndex, 3);
            member.setGroupAge(groupAge);
            String politicsStatus = (String) model.getValueAt(rowIndex, 4);
            member.setPoliticsStatus(politicsStatus);
            String national = (String) model.getValueAt(rowIndex, 5);
            member.setNational(national);
            String duty = (String) model.getValueAt(rowIndex, 6);
            member.setDuty(duty);
            String affiliated = (String) model.getValueAt(rowIndex, 7);
            member.setAffiliated(affiliated);
        }
        return member;
    }

    private void delete(ActionEvent e) {
        int[] selectedRows = memberTable.getSelectedRows();
        TableModel model = memberTable.getModel();
        ArrayList<String> idCardList = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            String idCard = (String) model.getValueAt(rowIndex, 0);
            idCardList.add(idCard);
        }
        try {
            MemberService.batchDeleteMember(idCardList);
            freshDate();
            Integer memberNum = MemberService.getMemberNum(loginUser.getDuty());
            gp.setMemNum(memberNum);
            GpService.updateGp(gp);
            setIndexInfo();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void addSpace(ActionEvent e) {
        String num = addNumField.getText();
        if (!StrUtil.isEmpty(num)) {
            int anInt = Integer.parseInt(num);
            Object[][] table = new Object[anInt][8];
            for (Object[] objects : table) {
                objects[7] = loginUser.getDuty();
            }
            addMemberTable.setModel(new DefaultTableModel(
                    table,
                    new String[]{
                            "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5e74\u9f84", "\u56e2\u9f84", "\u653f\u6cbb\u9762\u8c8c", "\u6c11\u65cf", "\u56e2\u5185\u804c\u52a1", "\u6240\u5c5e\u56e2\u652f\u90e8"
                    }
            ) {
                Class<?>[] columnTypes = new Class<?>[]{
                        String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class
                };
                boolean[] columnEditable = new boolean[]{
                        true, true, true, true, true, true, true, false
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
    }

    private void addMember(ActionEvent e) {
        TableModel model = addMemberTable.getModel();
        int rowCount = addMemberTable.getRowCount();
        ArrayList<Member> memberList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Member member = getMemberData(model, i);
            memberList.add(member);
        }
        try {
            MemberService.batchAddMember(memberList);
            addMemberTable.setModel(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                            "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5e74\u9f84", "\u56e2\u9f84", "\u653f\u6cbb\u9762\u8c8c", "\u6c11\u65cf", "\u56e2\u5185\u804c\u52a1", "\u6240\u5c5e\u56e2\u652f\u90e8"
                    }
            ) {
                Class<?>[] columnTypes = new Class<?>[]{
                        String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class
                };
                boolean[] columnEditable = new boolean[]{
                        true, true, true, true, true, true, true, false
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
            addNumField.setText("");
            Integer memberNum = MemberService.getMemberNum(loginUser.getDuty());
            gp.setMemNum(memberNum);
            GpService.updateGp(gp);
            setIndexInfo();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        mainFrame = new JFrame();
        tabbedPane = new JTabbedPane();
        indexPanel = new JPanel();
        layeredPane1 = new JLayeredPane();
        contentPanel = new JPanel();
        welcomeLabel = new JLabel();
        curGpLabel = new JLabel();
        supGpLabel = new JLabel();
        categoryLabel = new JLabel();
        industryLabel = new JLabel();
        memberNumLabel = new JLabel();
        background = new JLabel();
        selectPanel = new JPanel();
        vSpacer1 = new JPanel(null);
        vSpacer2 = new JPanel(null);
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        vSpacer3 = new JPanel(null);
        minAgeLabel = new JLabel();
        minAgeField = new JTextField();
        maxAgeLabel = new JLabel();
        maxAgeField = new JTextField();
        nameLabel = new JLabel();
        nameField = new JTextField();
        idCardLabel = new JLabel();
        idCardField = new JTextField();
        hSpacer3 = new JPanel(null);
        politicsStatusLabel = new JLabel();
        politicsStatusField = new JTextField();
        selectButton = new JButton();
        vSpacer4 = new JPanel(null);
        updateButton = new JButton();
        vSpacer5 = new JPanel(null);
        deleteButton = new JButton();
        vSpacer6 = new JPanel(null);
        scrollPane1 = new JScrollPane();
        memberTable = new JTable();
        addPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        addMemberTable = new JTable();
        vSpacer7 = new JPanel(null);
        vSpacer8 = new JPanel(null);
        hSpacer4 = new JPanel(null);
        addNumLabel = new JLabel();
        addNumField = new JTextField();
        vSpacer10 = new JPanel(null);
        hSpacer8 = new JPanel(null);
        hSpacer9 = new JPanel(null);
        addSpaceButton = new JButton();
        addMemberButton = new JButton();

        //======== mainFrame ========
        {
            mainFrame.setTitle("\u56e2\u5458\u7ba1\u7406\u7cfb\u7edf");
            mainFrame.setResizable(false);
            mainFrame.setIconImage(new ImageIcon(getClass().getResource("/img/logo.jpeg")).getImage());
            Container mainFrameContentPane = mainFrame.getContentPane();

            //======== tabbedPane ========
            {
                tabbedPane.setFont(tabbedPane.getFont().deriveFont(tabbedPane.getFont().getSize() + 7f));

                //======== indexPanel ========
                {

                    //======== layeredPane1 ========
                    {

                        //======== contentPanel ========
                        {
                            contentPanel.setOpaque(false);

                            //---- welcomeLabel ----
                            welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(welcomeLabel.getFont().getSize() + 8f));

                            //---- curGpLabel ----
                            curGpLabel.setFont(curGpLabel.getFont().deriveFont(curGpLabel.getFont().getSize() + 8f));

                            //---- supGpLabel ----
                            supGpLabel.setFont(supGpLabel.getFont().deriveFont(supGpLabel.getFont().getSize() + 8f));

                            //---- categoryLabel ----
                            categoryLabel.setFont(categoryLabel.getFont().deriveFont(categoryLabel.getFont().getSize() + 8f));

                            //---- industryLabel ----
                            industryLabel.setFont(industryLabel.getFont().deriveFont(industryLabel.getFont().getSize() + 8f));

                            //---- memberNumLabel ----
                            memberNumLabel.setFont(memberNumLabel.getFont().deriveFont(memberNumLabel.getFont().getSize() + 8f));

                            GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                            contentPanel.setLayout(contentPanelLayout);
                            contentPanelLayout.setHorizontalGroup(
                                    contentPanelLayout.createParallelGroup()
                                            .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                                    .addContainerGap(589, Short.MAX_VALUE)
                                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                            .addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(memberNumLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(curGpLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(industryLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(supGpLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                                    .addGap(196, 196, 196))
                            );
                            contentPanelLayout.setVerticalGroup(
                                    contentPanelLayout.createParallelGroup()
                                            .addGroup(contentPanelLayout.createSequentialGroup()
                                                    .addGap(102, 102, 102)
                                                    .addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(curGpLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(supGpLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(12, 12, 12)
                                                    .addComponent(industryLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(memberNumLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(43, Short.MAX_VALUE))
                            );
                        }
                        layeredPane1.add(contentPanel, JLayeredPane.PALETTE_LAYER);
                        contentPanel.setBounds(0, 0, 1085, 485);

                        //---- background ----
                        background.setIcon(new ImageIcon(getClass().getResource("/img/indexBg.png")));
                        layeredPane1.add(background, JLayeredPane.DEFAULT_LAYER);
                        background.setBounds(0, 0, 1085, 485);
                    }

                    GroupLayout indexPanelLayout = new GroupLayout(indexPanel);
                    indexPanel.setLayout(indexPanelLayout);
                    indexPanelLayout.setHorizontalGroup(
                            indexPanelLayout.createParallelGroup()
                                    .addComponent(layeredPane1, GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                    );
                    indexPanelLayout.setVerticalGroup(
                            indexPanelLayout.createParallelGroup()
                                    .addComponent(layeredPane1, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u9996\u9875", indexPanel);

                //======== selectPanel ========
                {

                    //---- minAgeLabel ----
                    minAgeLabel.setText("\u6700\u5c0f\u5e74\u9f84:");
                    minAgeLabel.setFont(minAgeLabel.getFont().deriveFont(minAgeLabel.getFont().getSize() + 5f));

                    //---- minAgeField ----
                    minAgeField.setFont(minAgeField.getFont().deriveFont(minAgeField.getFont().getSize() + 5f));

                    //---- maxAgeLabel ----
                    maxAgeLabel.setText("\u6700\u5927\u5e74\u9f84:");
                    maxAgeLabel.setFont(maxAgeLabel.getFont().deriveFont(maxAgeLabel.getFont().getSize() + 5f));

                    //---- maxAgeField ----
                    maxAgeField.setFont(maxAgeField.getFont().deriveFont(maxAgeField.getFont().getSize() + 5f));

                    //---- nameLabel ----
                    nameLabel.setText("\u59d3\u540d\uff1a");
                    nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getSize() + 5f));

                    //---- nameField ----
                    nameField.setFont(nameField.getFont().deriveFont(nameField.getFont().getSize() + 5f));

                    //---- idCardLabel ----
                    idCardLabel.setText("\u8eab\u4efd\u8bc1\uff1a");
                    idCardLabel.setFont(idCardLabel.getFont().deriveFont(idCardLabel.getFont().getSize() + 5f));

                    //---- idCardField ----
                    idCardField.setFont(idCardField.getFont().deriveFont(idCardField.getFont().getSize() + 5f));

                    //---- politicsStatusLabel ----
                    politicsStatusLabel.setText("\u653f\u6cbb\u9762\u8c8c:");
                    politicsStatusLabel.setFont(politicsStatusLabel.getFont().deriveFont(politicsStatusLabel.getFont().getSize() + 5f));

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

                    //======== scrollPane1 ========
                    {

                        //---- memberTable ----
                        memberTable.setFont(memberTable.getFont().deriveFont(memberTable.getFont().getSize() + 5f));
                        memberTable.setModel(new DefaultTableModel(
                                new Object[][]{
                                },
                                new String[]{
                                        "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5e74\u9f84", "\u56e2\u9f84", "\u653f\u6cbb\u9762\u8c8c", "\u6c11\u65cf", "\u56e2\u5185\u804c\u52a1", "\u6240\u5c5e\u56e2\u652f\u90e8"
                                }
                        ) {
                            Class<?>[] columnTypes = new Class<?>[]{
                                    String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class
                            };
                            boolean[] columnEditable = new boolean[]{
                                    false, true, true, true, true, true, true, false
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
                        memberTable.setAutoCreateRowSorter(true);
                        memberTable.setRowHeight(35);
                        memberTable.setShowVerticalLines(true);
                        memberTable.setShowHorizontalLines(true);
                        scrollPane1.setViewportView(memberTable);
                    }

                    GroupLayout selectPanelLayout = new GroupLayout(selectPanel);
                    selectPanel.setLayout(selectPanelLayout);
                    selectPanelLayout.setHorizontalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addComponent(vSpacer1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                                    .addGroup(GroupLayout.Alignment.TRAILING, selectPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(vSpacer2, GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
                                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                                            .addComponent(hSpacer1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(vSpacer6, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(idCardLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                                    .addComponent(nameLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                                    .addComponent(maxAgeLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                                    .addComponent(minAgeLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                                    .addComponent(idCardField, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(minAgeField, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                                                            .addComponent(maxAgeField, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                                                            .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                                                            .addGap(7, 7, 7)
                                                            .addComponent(hSpacer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                                    .addComponent(politicsStatusLabel)
                                                                    .addComponent(politicsStatusField, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(vSpacer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                                                            .addGap(0, 152, Short.MAX_VALUE)
                                                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                                                    .addComponent(selectButton, GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(updateButton, GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(deleteButton, GroupLayout.Alignment.TRAILING)))
                                                                    .addComponent(vSpacer4, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                                                    .addComponent(vSpacer5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(hSpacer2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)))
                                            .addContainerGap())
                                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                    );
                    selectPanelLayout.setVerticalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(vSpacer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(vSpacer3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(selectPanelLayout.createSequentialGroup()
                                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                            .addComponent(minAgeLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(minAgeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                            .addComponent(maxAgeLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(maxAgeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                            .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                            .addComponent(idCardLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(idCardField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                            .addComponent(hSpacer1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(hSpacer2, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                                            .addComponent(vSpacer6, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                                                    .addComponent(hSpacer3, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                                            .addComponent(politicsStatusLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(politicsStatusField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                                            .addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(vSpacer4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(vSpacer5, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(vSpacer2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap())
                    );
                }
                tabbedPane.addTab("\u56e2\u5458\u4fe1\u606f\u67e5\u8be2\u66f4\u65b0\u5220\u9664", selectPanel);

                //======== addPanel ========
                {

                    //======== scrollPane2 ========
                    {

                        //---- addMemberTable ----
                        addMemberTable.setAutoCreateRowSorter(true);
                        addMemberTable.setFont(addMemberTable.getFont().deriveFont(addMemberTable.getFont().getSize() + 5f));
                        addMemberTable.setRowHeight(35);
                        addMemberTable.setModel(new DefaultTableModel(
                                new Object[][]{
                                },
                                new String[]{
                                        "\u8eab\u4efd\u8bc1", "\u59d3\u540d", "\u5e74\u9f84", "\u56e2\u9f84", "\u653f\u6cbb\u9762\u8c8c", "\u6c11\u65cf", "\u56e2\u5185\u804c\u52a1", "\u6240\u5c5e\u56e2\u652f\u90e8"
                                }
                        ) {
                            Class<?>[] columnTypes = new Class<?>[]{
                                    String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class
                            };
                            boolean[] columnEditable = new boolean[]{
                                    true, true, true, true, true, true, true, false
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
                        addMemberTable.setShowHorizontalLines(true);
                        addMemberTable.setShowVerticalLines(true);
                        scrollPane2.setViewportView(addMemberTable);
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

                    //---- addMemberButton ----
                    addMemberButton.setText("\u6dfb\u52a0\u56e2\u5458");
                    addMemberButton.setFont(addMemberButton.getFont().deriveFont(addMemberButton.getFont().getSize() + 5f));
                    addMemberButton.addActionListener(e -> addMember(e));

                    GroupLayout addPanelLayout = new GroupLayout(addPanel);
                    addPanel.setLayout(addPanelLayout);
                    addPanelLayout.setHorizontalGroup(
                            addPanelLayout.createParallelGroup()
                                    .addComponent(scrollPane2)
                                    .addGroup(addPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(addPanelLayout.createParallelGroup()
                                                    .addComponent(vSpacer7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(vSpacer8, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(addPanelLayout.createSequentialGroup()
                                                            .addComponent(hSpacer4, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(addNumLabel)
                                                            .addGap(12, 12, 12)
                                                            .addComponent(addNumField, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(vSpacer10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(addSpaceButton)
                                                                    .addComponent(addMemberButton))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(hSpacer9, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                                                                    .addComponent(hSpacer8, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))))
                                            .addContainerGap())
                    );
                    addPanelLayout.setVerticalGroup(
                            addPanelLayout.createParallelGroup()
                                    .addGroup(addPanelLayout.createSequentialGroup()
                                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(vSpacer7, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(addPanelLayout.createParallelGroup()
                                                    .addGroup(addPanelLayout.createSequentialGroup()
                                                            .addGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(addNumField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(addNumLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                            .addGap(42, 42, 42))
                                                    .addGroup(addPanelLayout.createSequentialGroup()
                                                            .addGroup(addPanelLayout.createParallelGroup()
                                                                    .addComponent(vSpacer10, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                                    .addComponent(hSpacer4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(addPanelLayout.createSequentialGroup()
                                                                            .addGroup(addPanelLayout.createParallelGroup()
                                                                                    .addComponent(addSpaceButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(hSpacer9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                            .addGap(21, 21, 21)
                                                                            .addGroup(addPanelLayout.createParallelGroup()
                                                                                    .addComponent(addMemberButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(hSpacer8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                            .addComponent(vSpacer8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap())
                    );
                }
                tabbedPane.addTab("\u56e2\u5458\u8f6c\u63a5", addPanel);
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                    mainFrameContentPaneLayout.createParallelGroup()
                            .addComponent(tabbedPane)
            );
            mainFrameContentPaneLayout.setVerticalGroup(
                    mainFrameContentPaneLayout.createParallelGroup()
                            .addComponent(tabbedPane)
            );
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    private User loginUser;

    private Gp gp;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame mainFrame;
    private JTabbedPane tabbedPane;
    private JPanel indexPanel;
    private JLayeredPane layeredPane1;
    private JPanel contentPanel;
    private JLabel welcomeLabel;
    private JLabel curGpLabel;
    private JLabel supGpLabel;
    private JLabel categoryLabel;
    private JLabel industryLabel;
    private JLabel memberNumLabel;
    private JLabel background;
    private JPanel selectPanel;
    private JPanel vSpacer1;
    private JPanel vSpacer2;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JPanel vSpacer3;
    private JLabel minAgeLabel;
    private JTextField minAgeField;
    private JLabel maxAgeLabel;
    private JTextField maxAgeField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel idCardLabel;
    private JTextField idCardField;
    private JPanel hSpacer3;
    private JLabel politicsStatusLabel;
    private JTextField politicsStatusField;
    private JButton selectButton;
    private JPanel vSpacer4;
    private JButton updateButton;
    private JPanel vSpacer5;
    private JButton deleteButton;
    private JPanel vSpacer6;
    private JScrollPane scrollPane1;
    private JTable memberTable;
    private JPanel addPanel;
    private JScrollPane scrollPane2;
    private JTable addMemberTable;
    private JPanel vSpacer7;
    private JPanel vSpacer8;
    private JPanel hSpacer4;
    private JLabel addNumLabel;
    private JTextField addNumField;
    private JPanel vSpacer10;
    private JPanel hSpacer8;
    private JPanel hSpacer9;
    private JButton addSpaceButton;
    private JButton addMemberButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
