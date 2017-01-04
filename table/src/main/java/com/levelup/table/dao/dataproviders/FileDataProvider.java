package com.levelup.table.dao.dataproviders;

import com.levelup.table.dao.DataProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @author Veronika Kulichenko on 01.01.17.
 */
public class FileDataProvider implements DataProvider {

  private final File file;
  private final RandomAccessFile dataFile;

  public FileDataProvider(final String filePath) throws FileNotFoundException {
    this.file = new File(filePath);
    this.dataFile = new RandomAccessFile(file, "rw");
  }

  public RandomAccessFile getDataFile() {
    return dataFile;
  }


  @Override
  public void openConnection() {
  }

  @Override
  public void closeConnection() {
  }
}
