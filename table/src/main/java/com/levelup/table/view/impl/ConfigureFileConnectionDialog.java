package com.levelup.table.view.impl;

import com.levelup.table.view.Dialog;
import java.io.File;
import javax.swing.*;

/**
 * @author Veronika Kulichenko on 08.01.17.
 */
public class ConfigureFileConnectionDialog extends Dialog {

  private JFileChooser filePath = new JFileChooser();
  private JTextField selectedPath = new JTextField();

  public ConfigureFileConnectionDialog(){
    super();
    setBounds(300, 400, 450, 150);
    panel.setBounds(0, 0, 450, 150);
    ok.setBounds(30, 80, 80, 30);
    back.setBounds(130, 80, 80, 30);
    initComponents();
  }

  private void initComponents() {
    initLabels();
    initTextFields();
  }

  private void initLabels() {
    JLabel filePathLabel = new JLabel("File path:");

    filePathLabel.setBounds(30, 0, 65, 25);

    panel.add(filePathLabel);
  }

  private void initTextFields() {
    selectedPath.setBounds(30, 35, 370, 25);

    panel.add(filePath);
    panel.add(selectedPath);

    filePath.setCurrentDirectory(new File("."));
    filePath.setDialogTitle("Choose data location");
    filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//    filePath.setAcceptAllFileFilterUsed(false);

    if (filePath.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      selectedPath.setText(filePath.getSelectedFile().getAbsolutePath());
    } else {
      selectedPath.setText(filePath.getFileSystemView().getHomeDirectory().getAbsolutePath());
    }
  }

  public String getSelectedPath() {
    return selectedPath.getText();
  }

  public void setSelectedPath(String selectedPath) {
    this.selectedPath.setText(selectedPath);
  }

  @Override
  public Object get() {
    return null;
  }
}
