/*
 * Created by JFormDesigner on Thu Nov 03 11:11:36 CST 2022
 */

package com.cylmms.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ekertree
 */
public class ErrorDialog extends JDialog {
    public ErrorDialog(Window owner) {
        super(owner);
        initComponents();
        jFrame = (JFrame) owner;
    }

    public static void main(String[] args) {
        new ErrorDialog(new JFrame()).error("fff");
    }

    public void error(String msg) {
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMsg(msg);
        this.setVisible(true);
    }

    public void closeView() {
        this.dispose();
        jFrame.dispose();
    }


    public void setMsg(String msg) {
        errorMsg.setText(msg);
    }

    private void thisWindowClosing(WindowEvent e) {
        closeView();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        errorMsg = new JTextArea();

        //======== this ========
        setResizable(false);
        setTitle("\u9519\u8bef");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //======== scrollPane1 ========
                {

                    //---- errorMsg ----
                    errorMsg.setEditable(false);
                    errorMsg.setFont(errorMsg.getFont().deriveFont(errorMsg.getFont().getSize() + 5f));
                    errorMsg.setForeground(Color.red);
                    scrollPane1.setViewportView(errorMsg);
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                );
                contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private final JFrame jFrame;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTextArea errorMsg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
