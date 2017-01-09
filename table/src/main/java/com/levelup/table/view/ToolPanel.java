package com.levelup.table.view;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.DataProvider;
import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.dataproviders.SQLDataProvider;
import com.levelup.table.dao.impl.csv.CitizenCSVDAOImpl;
import com.levelup.table.dao.impl.csv.StreetCSVDAOImpl;
import com.levelup.table.dao.impl.json.CitizenJsonDAOImpl;
import com.levelup.table.dao.impl.json.StreetJsonDAOImpl;
import com.levelup.table.dao.impl.sql.CitizenSQLDAOImpl;
import com.levelup.table.dao.impl.sql.StreetSQLDAOImpl;
import com.levelup.table.dao.impl.xml.CitizenXMLDAOImpl;
import com.levelup.table.dao.impl.xml.StreetXMLDAOImpl;
import com.levelup.table.entity.Citizen;
import com.levelup.table.entity.Street;
import com.levelup.table.view.impl.CitizenTablePanel;
import com.levelup.table.view.impl.ConfigureFileConnectionDialog;
import com.levelup.table.view.impl.ConfigureSQLConnectionDialog;
import com.levelup.table.view.impl.StreetTablePanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

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
    return e -> workingPanel.read();
  }

  public ActionListener updateListener() {
    return e -> workingPanel.update();
  }

  public ActionListener deleteListener() {
    return e -> workingPanel.delete();
  }

  public ActionListener connectListener() {
    return e -> {
      DataProvider.ConnectionType selectedConnectionType = getDataProviderConnectionType();
      Dialog dialog = initDialog(selectedConnectionType);
      if(dialog.isOkPressed()) {
        initProvider(selectedConnectionType, dialog);
        initTabs(provider, selectedConnectionType);
      } else {
        // TODO: 09.01.17 switch toggle button
      }
    };
  }

  private Dialog initDialog(final DataProvider.ConnectionType selectedConnectionType) {
    Dialog dialog = null;
    switch (selectedConnectionType) {
      case MYSQL:
        dialog = new ConfigureSQLConnectionDialog("//localhost:3306/address_book");
        break;
      case H2:
        dialog = new ConfigureSQLConnectionDialog("~/address_book");
        break;
      case XML:
      case CSV:
      case JSON:
      default:
        dialog = new ConfigureFileConnectionDialog();
    }
    dialog.setVisible(true);
    return dialog;
  }

  private DataProvider.ConnectionType getDataProviderConnectionType() {
    return DataProvider.ConnectionType.valueOf(((String) getConnectionType().getSelectedItem()).toUpperCase());
  }

  private void initProvider(DataProvider.ConnectionType connectionType, final Dialog connectionDialog) {
    switch (connectionType) {
      case H2:
        ConfigureSQLConnectionDialog h2Dialog = (ConfigureSQLConnectionDialog) connectionDialog;
        provider = new SQLDataProvider("jdbc:h2:" + h2Dialog.getUrl(), h2Dialog.getUser(), h2Dialog.getUser(), "org.h2.Driver");
        break;
      case MYSQL:
        ConfigureSQLConnectionDialog mysqlDialog = (ConfigureSQLConnectionDialog) connectionDialog;
        provider = new SQLDataProvider("jdbc:mysql:" + mysqlDialog.getUrl(), mysqlDialog.getUser(), mysqlDialog.getPass(), "com.mysql.jdbc.Driver");
        break;
      default:
        provider = new FileDataProvider("/Users/aleksandr/Nika/Programming/MyProjects/table");

    }
  }

  private void initTabs(DataProvider provider, DataProvider.ConnectionType connectionType) {
    CitizenTablePanel citizenTablePanel;
    StreetTablePanel streetTablePanel;
    DAO<Citizen> citizenDAO;
    DAO<Street> streetDAO;
    switch (connectionType) {
      case MYSQL:
      case H2:
        SQLDataProvider sqlDataProvider = (SQLDataProvider) provider;
        citizenDAO = new CitizenSQLDAOImpl(sqlDataProvider);
        streetDAO = new StreetSQLDAOImpl(sqlDataProvider);
        break;
      case XML:
        FileDataProvider xmlDataProvider = (FileDataProvider) provider;
        citizenDAO = new CitizenXMLDAOImpl(xmlDataProvider);
        streetDAO = new StreetXMLDAOImpl(xmlDataProvider);
        break;
      case CSV:
        FileDataProvider csvDataProvider = (FileDataProvider) provider;
        citizenDAO = new CitizenCSVDAOImpl(csvDataProvider);
        streetDAO = new StreetCSVDAOImpl(csvDataProvider);
        break;
      case JSON:
        FileDataProvider jsonDataProvider = (FileDataProvider) provider;
        citizenDAO = new CitizenJsonDAOImpl(jsonDataProvider);
        streetDAO = new StreetJsonDAOImpl(jsonDataProvider);
        break;
      //            case MONGODB:
      //
      //                break;
      default:
        FileDataProvider fileDataProvider = (FileDataProvider) provider;
        citizenDAO = new CitizenJsonDAOImpl(fileDataProvider);
        streetDAO = new StreetJsonDAOImpl(fileDataProvider);
    }
    provider.openConnection();
    citizenTablePanel = new CitizenTablePanel(citizenDAO);
    streetTablePanel = new StreetTablePanel(streetDAO);
    workingPanel.addTab(citizenTablePanel.getName(), citizenTablePanel);
    workingPanel.addTab(streetTablePanel.getName(), streetTablePanel);
  }

  public ActionListener disconnectListener() {
    return e -> {
      provider.closeConnection();
      workingPanel.removeAll();
      workingPanel.repaint();
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
    connectionType.addItem("JSON");
    connectionType.addItem("MySQL");
    connectionType.addItem("H2");
    connectionType.addItem("MongoDB");
    connectionType.addItem("CSV");
    connectionType.addItem("XML");
  }
}
