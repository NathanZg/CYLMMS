/*
 * Created by JFormDesigner on Mon Oct 31 17:29:05 CST 2022
 */

package com.cylmms.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author ekertree
 */
public class MainFrame {
    public MainFrame() {
        initComponents();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.openView();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        mainFrame = new JFrame();
        tabbedPane = new JTabbedPane();
        indexPanel = new JPanel();
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
        updatePanel = new JPanel();
        addPanel = new JPanel();
        deletePanel = new JPanel();

        //======== mainFrame ========
        {
            mainFrame.setTitle("\u56e2\u5458\u7ba1\u7406\u7cfb\u7edf");
            Container mainFrameContentPane = mainFrame.getContentPane();

            //======== tabbedPane ========
            {
                tabbedPane.setFont(tabbedPane.getFont().deriveFont(tabbedPane.getFont().getSize() + 7f));

                //======== indexPanel ========
                {

                    GroupLayout indexPanelLayout = new GroupLayout(indexPanel);
                    indexPanel.setLayout(indexPanelLayout);
                    indexPanelLayout.setHorizontalGroup(
                            indexPanelLayout.createParallelGroup()
                                    .addGap(0, 1030, Short.MAX_VALUE)
                    );
                    indexPanelLayout.setVerticalGroup(
                            indexPanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
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

                    //---- updateButton ----
                    updateButton.setText("\u66f4\u65b0");
                    updateButton.setFont(updateButton.getFont().deriveFont(updateButton.getFont().getSize() + 5f));

                    //---- deleteButton ----
                    deleteButton.setText("\u5220\u9664");
                    deleteButton.setFont(deleteButton.getFont().deriveFont(deleteButton.getFont().getSize() + 5f));

                    GroupLayout selectPanelLayout = new GroupLayout(selectPanel);
                    selectPanel.setLayout(selectPanelLayout);
                    selectPanelLayout.setHorizontalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addComponent(vSpacer1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                                    .addGroup(GroupLayout.Alignment.TRAILING, selectPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(selectPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(vSpacer2, GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
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
                                                                            .addGap(0, 126, Short.MAX_VALUE)
                                                                            .addGroup(selectPanelLayout.createParallelGroup()
                                                                                    .addComponent(selectButton, GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(updateButton, GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(deleteButton, GroupLayout.Alignment.TRAILING)))
                                                                    .addComponent(vSpacer4, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                                                    .addComponent(vSpacer5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(hSpacer2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)))
                                            .addContainerGap())
                    );
                    selectPanelLayout.setVerticalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addGroup(selectPanelLayout.createSequentialGroup()
                                            .addContainerGap(251, Short.MAX_VALUE)
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
                tabbedPane.addTab("\u56e2\u5458\u4fe1\u606f\u67e5\u8be2", selectPanel);

                //======== updatePanel ========
                {

                    GroupLayout updatePanelLayout = new GroupLayout(updatePanel);
                    updatePanel.setLayout(updatePanelLayout);
                    updatePanelLayout.setHorizontalGroup(
                            updatePanelLayout.createParallelGroup()
                                    .addGap(0, 1030, Short.MAX_VALUE)
                    );
                    updatePanelLayout.setVerticalGroup(
                            updatePanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u56e2\u5458\u4fe1\u606f\u4fee\u6539", updatePanel);

                //======== addPanel ========
                {

                    GroupLayout addPanelLayout = new GroupLayout(addPanel);
                    addPanel.setLayout(addPanelLayout);
                    addPanelLayout.setHorizontalGroup(
                            addPanelLayout.createParallelGroup()
                                    .addGap(0, 1030, Short.MAX_VALUE)
                    );
                    addPanelLayout.setVerticalGroup(
                            addPanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u56e2\u5458\u8f6c\u63a5", addPanel);

                //======== deletePanel ========
                {

                    GroupLayout deletePanelLayout = new GroupLayout(deletePanel);
                    deletePanel.setLayout(deletePanelLayout);
                    deletePanelLayout.setHorizontalGroup(
                            deletePanelLayout.createParallelGroup()
                                    .addGap(0, 1030, Short.MAX_VALUE)
                    );
                    deletePanelLayout.setVerticalGroup(
                            deletePanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u5220\u9664\u56e2\u7c4d", deletePanel);
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

    public void openView() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame mainFrame;
    private JTabbedPane tabbedPane;
    private JPanel indexPanel;
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
    private JPanel updatePanel;
    private JPanel addPanel;
    private JPanel deletePanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
