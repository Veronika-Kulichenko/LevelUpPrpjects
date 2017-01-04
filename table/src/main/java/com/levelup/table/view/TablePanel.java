package com.levelup.table.view;

import com.levelup.table.dao.DAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public abstract class TablePanel<T> extends JPanel implements Action {

    private final JTable table;
    private final Dialog<T> dialog;
    private final TableModelContainer<T> tableContainer;
    private final DAO<T> dao;

    public TablePanel(TableModelContainer<T> tableContainer, DAO<T> dao, Dialog<T> dialog) {
        this.tableContainer = tableContainer;
        this.table = new JTable(tableContainer);
        this.dao = dao;
        this.dialog = dialog;

        init();
    }

    private void init() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(595, 300));
        add(scrollPane);
        setSize(new Dimension(595, 300));

        List<T> data = dao.read();
        refreshDataView(data);
    }

    public void refreshDataView(List<T> data) {
        tableContainer.setData(data);
        tableContainer.clearUpdated();
        table.updateUI();
    }

    public T getSelectedRowData(int row) {
        return tableContainer.getSelectedRowData(row);
    }

    @Override
    public void create() {
        dialog.setVisible(true);
        if(dialog.isOkPressed()){
            dao.create(dialog.get());
            List<T> data = dao.read();
            refreshDataView(data);
        }
    }

    @Override
    public void read() {
        List<T> data = dao.read();
        refreshDataView(data);
    }

    @Override
    public void update() {
        for (T t : tableContainer.getUpdated()) {
            dao.update(t);
        }
        tableContainer.clearUpdated();
        List<T> data = dao.read();
        refreshDataView(data);
    }

    @Override
    public void delete() {
        T t = tableContainer.getSelectedRowData(table.getSelectedRow());
        dao.delete(t);
        List<T> data = tableContainer.getData();
        data.remove(t);
        refreshDataView(data);
    }

    public ActionListener connectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<T> data = dao.read();
                refreshDataView(data);
            }
        };
    }

    public ActionListener disconnectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDataView(new ArrayList<T>());
            }
        };
    }
}
