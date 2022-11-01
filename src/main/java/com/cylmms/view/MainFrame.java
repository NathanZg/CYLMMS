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
                                    .addGap(0, 973, Short.MAX_VALUE)
                    );
                    indexPanelLayout.setVerticalGroup(
                            indexPanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u9996\u9875", indexPanel);

                //======== selectPanel ========
                {

                    GroupLayout selectPanelLayout = new GroupLayout(selectPanel);
                    selectPanel.setLayout(selectPanelLayout);
                    selectPanelLayout.setHorizontalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addGap(0, 973, Short.MAX_VALUE)
                    );
                    selectPanelLayout.setVerticalGroup(
                            selectPanelLayout.createParallelGroup()
                                    .addGap(0, 483, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab("\u56e2\u5458\u4fe1\u606f\u67e5\u8be2", selectPanel);

                //======== updatePanel ========
                {

                    GroupLayout updatePanelLayout = new GroupLayout(updatePanel);
                    updatePanel.setLayout(updatePanelLayout);
                    updatePanelLayout.setHorizontalGroup(
                            updatePanelLayout.createParallelGroup()
                                    .addGap(0, 973, Short.MAX_VALUE)
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
                                    .addGap(0, 973, Short.MAX_VALUE)
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
                                    .addGap(0, 973, Short.MAX_VALUE)
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
    private JPanel updatePanel;
    private JPanel addPanel;
    private JPanel deletePanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
