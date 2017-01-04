package com.levelup.table.view;

import javax.swing.*;

/**
 * Created by java on 30.07.2016.
 */
public class TabbedPane extends JTabbedPane implements Action {

    @Override
    public void create() {
        TablePanel panel = (TablePanel) getSelectedComponent();
        panel.create();
    }

    @Override
    public void read() {
        TablePanel panel = (TablePanel) getSelectedComponent();
        panel.read();
    }

    @Override
    public void update() {
        TablePanel panel = (TablePanel) getSelectedComponent();
        panel.update();
    }

    @Override
    public void delete() {
        TablePanel panel = (TablePanel) getSelectedComponent();
        panel.delete();
    }
}
