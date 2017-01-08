package com.levelup.table.dao.dataproviders;

import com.levelup.table.dao.DataProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Veronika Kulichenko on 01.01.17.
 */
public class FileDataProvider implements DataProvider {

  private Map<String, RandomAccessFile> dataMap = new HashMap<>();
  private final String packagePath;
  private StringBuilder files = new StringBuilder();
  private static final String READ_WRITE_ACCESS = "rw";

  public FileDataProvider(final String packagePath) {
    this.packagePath = packagePath;
  }

  public RandomAccessFile getDataFile(String fileName) {
    return dataMap.get(fileName);
  }

  @Override
  public void openConnection() {
    try {
      for (String fileName : files.toString().split(";")) {
        dataMap.put(fileName, new RandomAccessFile(new File(packagePath + File.separator + fileName), READ_WRITE_ACCESS));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void closeConnection() {
    try {
      for(RandomAccessFile dataFile : dataMap.values()){
        dataFile.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void appendFile(final String fileName) {
    files.append(fileName + ";");
  }
}
