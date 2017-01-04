package com.levelup.table.dao.impl.xml;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.impl.json.StreetJsonDAOImpl;
import com.levelup.table.entity.Entity;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Veronika Kulichenko on 03.01.17.
 */
public abstract class AbstractXMLDAO<T extends Entity> implements DAO<T> {

  protected final FileDataProvider fileDataProvider;
  private static final Logger LOG = Logger.getLogger(StreetJsonDAOImpl.class.getName());
  private static long id = 1;

  private final String HEADER_XML;
  private final String TAIL_XML;
  private final String OPEN_TAG;
  private final String CLOSE_TAG;

  private static final String ID_TAG = "<id>%d</id>";

  public AbstractXMLDAO(final FileDataProvider fileDataProvider, final String entityListName, final String entityName) {
    this.fileDataProvider = fileDataProvider;
    this.HEADER_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<" + entityListName + ">";
    this.TAIL_XML = "</" + entityListName + ">";
    this.OPEN_TAG = "<" + entityName + ">";
    this.CLOSE_TAG = "</" + entityName + ">";
  }

  public abstract String viewEntity(T t);

  protected abstract T parseEntity(final String str);

  @Override
  public void create(final T t) {
    RandomAccessFile file = fileDataProvider.getDataFile();
    try {
      if ((t.getId() == null) || (t.getId() == 0L)) {
        t.setId(id++);
      }
      if (file.length() < (HEADER_XML.length() + TAIL_XML.length())) {
        file.write((HEADER_XML + "\n").getBytes());
      } else {
        file.seek(file.length() - (TAIL_XML.length()));
      }
      file.write(viewEntity(t).getBytes());
      file.write(("\n" + TAIL_XML).getBytes());

    } catch (IOException ex) {
      LOG.log(Level.INFO, "create entity error", ex);
    }
  }

  @Override
  public ArrayList<T> read() {
    RandomAccessFile file = fileDataProvider.getDataFile();
    ArrayList<T> streetList = new ArrayList<>();
    String str = "";
    String myStr = "";
    try {
      while ((str = file.readLine()) != null) {
        if (HEADER_XML.contains(str) || str.contains(OPEN_TAG)) continue;
        if (str.contains(CLOSE_TAG)) {
          streetList.add(parseEntity(myStr));
          myStr = "";
        } else myStr += str.trim() + "|";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return streetList;
  }

  @Override
  public void update(final T t) {
    RandomAccessFile file = fileDataProvider.getDataFile();
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
      s += (end + 1 + TAIL_XML.length()) < file.length() ? "\n" : "\n";
      file.write(s.getBytes());
      file.write(buffer.getBytes());
      file.setLength(start + s.length() + buffer.length() - 1);
    } catch (IOException e) {
      System.out.println("Error get info from file JSON (Street)");
    }
  }

  @Override
  public void delete(final T t) {
    RandomAccessFile file = fileDataProvider.getDataFile();
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
    RandomAccessFile file = fileDataProvider.getDataFile();
    String entityId = String.format(ID_TAG, id);
    String str;
    String myStr = "";
    T t = null;
    boolean found = false;
    try {
      while ((str = file.readLine()) != null) {
        if (found && str.contains(CLOSE_TAG)) break;
        if (str.contains(entityId)) {
          found = true;
          myStr = str;
          continue;
        }
        if (found) {
          myStr += "|" + str;
        }
      }
      t = parseEntity(myStr);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return t;
  }

  public int[] getStartAndEndOfStr(final RandomAccessFile file, final T t) throws IOException {
    int[] arr = new int[2];
    int start = 0;
    int end = 0;
    String entityId = String.format(ID_TAG, id);
    boolean found = false;
    String str = "";
    while ((str = file.readLine()) != null && !found) {
      if (str.contains(entityId)) {
        found = true;
        start -= OPEN_TAG.length();
        arr[0] = start;
      }
      if (!found) {
        start += str.length() + 1;
      } else if (!str.contains(CLOSE_TAG)) {
        if (end == 0) {
          end = start + OPEN_TAG.length() + str.length() + 1;
        } else {
          end += str.length() + 1;
        }
      } else {
        end += CLOSE_TAG.length();
        arr[1] = end;
        break;
      }
    }
    return arr;
  }

}
