package com.levelup.table.view;

import com.levelup.table.dao.DataProvider;
import com.levelup.table.dao.impl.sql.CitizenSQLDAOImpl;
import com.levelup.table.dao.dataproviders.SQLDataProvider;
import com.levelup.table.dao.impl.sql.StreetSQLDAOImpl;
import com.levelup.table.view.impl.CitizenTablePanel;
import com.levelup.table.view.impl.StreetTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolPanel extends JPanel {

    private final TabbedPane workingPanel;
    private DataProvider provider;
    private JComboBox<String> connectionType;


    public ToolPanel(TabbedPane workingPanel) {
        this.workingPanel = workingPanel;
        initLayout();
    }

    private void initLayout() {
        setSize(new Dimension(200, 400));
        setVisible(true);

        initButtons();
    }

    private void initButtons() {
        connectionType = getConnectionType();
        add(connectionType);

        ButtonGroup bg = new ButtonGroup();
        JToggleButton btnConnect = new JToggleButton("Connect");
        JToggleButton btnDisConnect = new JToggleButton("Disconnect");

        JButton btnCreate = new JButton("create");
        JButton btnRead = new JButton("read");
        JButton btnUpdate = new JButton("update");
        JButton btnDelete = new JButton("delete");

        bg.add(btnConnect);
        bg.add(btnDisConnect);
        add(btnConnect);
        add(btnDisConnect);
        add(btnCreate);
        add(btnRead);
        add(btnUpdate);
        add(btnDelete);

        connectionType.setBounds(0, 310, 125, 20);
        btnConnect.setBounds(25, 0 + 310, 125, 20);
        btnDisConnect.setBounds(25, 30 + 310, 125, 20);
        btnCreate.setBounds(100 * 0 + 200, 310, 75, 50);
        btnRead.setBounds(100 * 1 + 200, 310, 75, 50);
        btnUpdate.setBounds(100 * 2 + 200, 310, 75, 50);
        btnDelete.setBounds(100 * 3 + 200, 310, 75, 50);

        btnConnect.addActionListener(connectListener());
        btnDisConnect.addActionListener(disconnectListener());
        btnCreate.addActionListener(createListener());
        btnRead.addActionListener(readListener());
        btnUpdate.addActionListener(updateListener());
        btnDelete.addActionListener(deleteListener());
    }


    public ActionListener createListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingPanel.create();
            }
        };
    }

    public ActionListener readListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingPanel.read();
            }
        };
    }

    public ActionListener updateListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingPanel.update();
            }
        };
    }

    public ActionListener deleteListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingPanel.delete();
            }
        };
    }

    public ActionListener connectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataProvider.ConnectionType connectionType = getDataProviderConnectionType();
                initProvider(connectionType);
                initTabs(provider, connectionType);
            }
        };
    }

    private DataProvider.ConnectionType getDataProviderConnectionType() {
        return DataProvider.ConnectionType.valueOf(((String) getConnectionType().getSelectedItem()).toUpperCase());
    }

    private void initProvider(DataProvider.ConnectionType connectionType) {
        switch (connectionType){
            case H2:
                provider = new SQLDataProvider("jdbc:h2:~/address_book", "root", "root", "org.h2.Driver");
                break;
            case MYSQL:
            default:
                provider = new SQLDataProvider("jdbc:mysql://localhost:3306/address_book",
                        "root",
                        "root",
                        "com.mysql.jdbc.Driver");
        }
        provider.openConnection();
    }

    private void initTabs(DataProvider provider, DataProvider.ConnectionType connectionType) {
        CitizenTablePanel citizenTablePanel;
        StreetTablePanel streetTablePanel;

        switch (connectionType) {
//            case MONGODB:
//
//                break;
            default:
                SQLDataProvider sqlDataProvider = (SQLDataProvider)provider;
                citizenTablePanel = new CitizenTablePanel(new CitizenSQLDAOImpl(sqlDataProvider));
                streetTablePanel = new StreetTablePanel(new StreetSQLDAOImpl(sqlDataProvider));
        }

        workingPanel.addTab("Citizen Tab", citizenTablePanel);
        workingPanel.addTab("Street Tab", streetTablePanel);
    }

    public ActionListener disconnectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provider.closeConnection();
                workingPanel.removeAll();
                workingPanel.repaint();
            }
        };
    }

    public JComboBox<String> getConnectionType() {
        if (connectionType == null) {
            connectionType = new JComboBox<>();
            addConnectionTypeList(connectionType);
        }
        return connectionType;
    }

    private void addConnectionTypeList(JComboBox<String> connectionType) {
        connectionType.addItem("MySQL");
        connectionType.addItem("H2");
        connectionType.addItem("MongoDB");
    }
}
