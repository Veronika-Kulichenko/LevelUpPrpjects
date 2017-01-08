package com.levelup.table.dao.impl.json;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.impl.AbstractFileDAO;
import com.levelup.table.entity.Entity;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
public abstract class AbstractJSONDAO<T extends Entity> extends AbstractFileDAO<T> {

  private static final Logger LOG = Logger.getLogger(AbstractJSONDAO.class.getName());

  private final String HEADER_JSON;
  private static final String TAIL_JSON = "\n]}";

  protected AbstractJSONDAO(final FileDataProvider fileDataProvider, String fileName, final String entityName) {
    super(fileDataProvider, fileName);
    this.HEADER_JSON = "{\"" + entityName + "\":[";
  }

  @Override
  public void create(final T t) {
    RandomAccessFile file = getDataFile();
    try {
      if ((t.getId() == null) || (t.getId() == 0L)) {
        t.setId(getNextId());
      }
      if (file.length() < (HEADER_JSON.length() + TAIL_JSON.length())) {
        file.write((HEADER_JSON + "\n").getBytes());
      } else {
        file.seek(file.length() - (TAIL_JSON.length()));
        file.write(",\n".getBytes());
      }
      file.write(viewEntity(t).getBytes());
      file.write(TAIL_JSON.getBytes());

    } catch (IOException ex) {
      LOG.log(Level.INFO, "create entity error", ex);
    }
  }

  @Override
  public ArrayList<T> read() {
    ArrayList<T> result = new ArrayList();
    RandomAccessFile file = getDataFile();
    try {
      file.seek(0);
      String str;
      // read lines till the end of the stream
      while ((str = file.readLine()) != null) {
        if (str.contains("id")) {
          result.add(parseEntity(str));
        }
      }
    } catch (IOException e) {
      System.out.println("Error get info from file JSON (Street)");
    }
    return result;
  }

  protected abstract T parseEntity(final String str);

  @Override
  public void update(final T t) {
    RandomAccessFile file = getDataFile();
    try {
      String buffer = "";
      file.seek(0);
      String str;
      int[] startAndEndOfStr = getStartAndEndOfStr(file, t);
      int start = startAndEndOfStr[0];
      int end = startAndEndOfStr[1];
      file.seek(end);
      while ((str = file.readLine()) != null) {
        buffer += str + "\n";
      }
      file.seek(start);
      String s = viewEntity(t);
      s += (end + 1 + TAIL_JSON.length()) < file.length() ? ",\n" : "\n";
      file.write(s.getBytes());
      file.write(buffer.getBytes());
      file.setLength(start + s.length() + buffer.length() - 1);
    } catch (IOException e) {
      System.out.println("Error get info from file JSON (Street)");
    }
  }

  @Override
  public void delete(final T t) {
    RandomAccessFile file = getDataFile();
    try {
      String buffer = "";
      file.seek(0);
      String str;
      int startAndEndOfStr[] = getStartAndEndOfStr(file, t);
      int start = startAndEndOfStr[0];
      int end = startAndEndOfStr[1];
      file.seek(end);
      while ((str = file.readLine()) != null) {
        buffer += str + "\n";
      }
      file.seek(start);
      file.write(buffer.getBytes());
      file.setLength(start + buffer.length() - 1);
    } catch (IOException e) {
      System.out.println("Error get info from file JSON (Street)");
    }
  }

  @Override
  public T getOneById(final long id) {
    T t = null;
    RandomAccessFile file = getDataFile();
    String str;
    try {
      while ((str = file.readLine()) != null) {
        if (str.contains("{id:" + id)) {
          t = parseEntity(str);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return t;
  }

  public abstract String viewEntity(T t);

  public int[] getStartAndEndOfStr(RandomAccessFile file, T t) throws IOException {
    int[] arr = new int[2];
    int start = 0;
    int end = 0;
    boolean found = false;
    String str = "";
    while ((str = file.readLine()) != null && !found) {
      if (str.contains("{id:" + t.getId())) {
        found = true;
      }
      if (!found) {
        start += str.length() + 1;
        arr[0] = start;
      } else {
        end = start + str.length() + 1;
        arr[1] = end;
      }
    } return arr;
  }

  @Override
  protected long initMaxId() {
    long maxId = 0;
    RandomAccessFile file = getDataFile();
    try {
      file.seek(0);
      String str = "";
      while ((str = file.readLine()) != null) {
        if (str.contains("id")) {
          long id = Long.parseLong(str.replaceAll("\\{id:|\\w+:|\\}|\\s+", "").split(",")[0]);
          if (maxId < id) maxId = id;
        }
      }
    } catch (IOException e) {
      LOG.log(Level.INFO, "error during initialization id", e);
    }
    return maxId;
  }
}
