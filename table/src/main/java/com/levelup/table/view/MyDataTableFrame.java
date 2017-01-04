package com.levelup.table.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by java on 30.07.2016.
 */
public class MyDataTableFrame extends JFrame {

    public MyDataTableFrame() throws HeadlessException {
        init();
    }

    private void init() {
        Container container = getContentPane();

        TabbedPane tabbedPane = new TabbedPane();

        container.add(tabbedPane, BorderLayout.CENTER);
        container.add(new ToolPanel(tabbedPane), BorderLayout.PAGE_END);

        setVisible(true);
        setBounds(0, 0, 800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
