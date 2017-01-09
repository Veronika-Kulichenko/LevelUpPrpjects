package com.levelup.table.view.impl;

import com.levelup.table.view.Dialog;
import javax.swing.*;

/**
 * @author Veronika Kulichenko on 08.01.17.
 */
public class ConfigureSQLConnectionDialog extends Dialog {

  private JTextField url = new JTextField();
  private JTextField user = new JTextField("root");
  private JTextField pass = new JTextField("root");

  public ConfigureSQLConnectionDialog(final String url) {
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

    urlLabel.setBounds(30, 0, 65, 25);
    userLabel.setBounds(30, 35, 65, 25);
    passLabel.setBounds(30, 70, 65, 25);

    panel.add(urlLabel);
    panel.add(userLabel);
    panel.add(passLabel);
  }

  private void initTextFields() {
    url.setBounds(110, 0, 100, 25);
    user.setBounds(110, 35, 100, 25);
    pass.setBounds(110, 70, 100, 25);

    panel.add(url);
    panel.add(user);
    panel.add(pass);
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

  @Override
  public Object get() {
    return null;
  }
}
