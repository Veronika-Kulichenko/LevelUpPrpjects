package com.levelup.table.dao.impl;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Entity;
import java.io.RandomAccessFile;

/**
 * @author Veronika Kulichenko on 07.01.17.
 */
public abstract class AbstractFileDAO<T extends Entity> implements DAO<T> {

  private Long id;
  protected final FileDataProvider fileDataProvider;
  private String fileName;

  public AbstractFileDAO(FileDataProvider fileDataProvider, String fileName) {
    this.fileDataProvider = fileDataProvider;
    this.fileName = fileName;
    fileDataProvider.appendFile(fileName);
  }

  public RandomAccessFile getDataFile() {
    return fileDataProvider.getDataFile(fileName);
  }

  protected long getNextId() {
    if (null == id) id = initMaxId();
    return ++id;
  }

  protected abstract long initMaxId();

}
