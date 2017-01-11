package com.levelup.table.view.impl;

import com.levelup.table.entity.Street;
import com.levelup.table.view.Dialog;
import javax.swing.*;

/**
 * @author Veronika Kulichenko on 11.01.17.
 */
public class ConfigureMongoDBConnectionDialog extends Dialog {
  private JTextField url = new JTextField();
  private JTextField user = new JTextField("root");
  private JTextField pass = new JTextField("root");
  private JTextField database = new JTextField("address-book");

  public ConfigureMongoDBConnectionDialog(final String url) {
    super();
    initComponents();
    this.url.setText(url);
  }

  private void initComponents() {
    initLabels();
    initTextFields();
  }

  private void initLabels() {
    JLabel urlLabel = new JLabel("URL:");
    JLabel userLabel = new JLabel("User:");
    JLabel passLabel = new JLabel("Password:");
    JLabel databaseLabel = new JLabel("Database:");

    urlLabel.setBounds(30, 0, 65, 25);
    userLabel.setBounds(30, 35, 65, 25);
    passLabel.setBounds(30, 70, 65, 25);
    databaseLabel.setBounds(30, 105, 65, 25);

    panel.add(urlLabel);
    panel.add(userLabel);
    panel.add(passLabel);
    panel.add(databaseLabel);
  }

  private void initTextFields() {
    url.setBounds(110, 0, 100, 25);
    user.setBounds(110, 35, 100, 25);
    pass.setBounds(110, 70, 100, 25);
    database.setBounds(110, 105, 100, 25);

    panel.add(url);
    panel.add(user);
    panel.add(pass);
    panel.add(database);
  }

  public String getUrl() {
    return url.getText();
  }

  public void setUrl(final JTextField url) {
    this.url = url;
  }

  public String getUser() {
    return user.getText();
  }

  public void setUser(final JTextField user) {
    this.user = user;
  }

  public String getPass() {
    return pass.getText();
  }

  public void setPass(final JTextField pass) {
    this.pass = pass;
  }

  public String getDatabase() {
    return database.getText();
  }

  public void setDatabase(final JTextField database) {
    this.database = database;
  }

  @Override
  public Object get() {
    return null;
  }
}
