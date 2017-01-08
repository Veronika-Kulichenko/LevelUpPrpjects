package com.levelup.table.dao.impl.xml;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.impl.AbstractFileDAO;
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
public abstract class AbstractXMLDAO<T extends Entity> extends AbstractFileDAO<T> {

  private static final Logger LOG = Logger.getLogger(StreetJsonDAOImpl.class.getName());

  private final String HEADER_XML;
  private final String TAIL_XML;
  private final String OPEN_TAG;
  private final String CLOSE_TAG;

  private static final String ID_TAG = "<id>%d</id>";

  public AbstractXMLDAO(final FileDataProvider fileDataProvider, String fileName, final String entityListName, final String entityName) {
    super(fileDataProvider, fileName);
    this.HEADER_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<" + entityListName + ">";
    this.TAIL_XML = "</" + entityListName + ">";
    this.OPEN_TAG = "\t<" + entityName + ">\n";
    this.CLOSE_TAG = "\t</" + entityName + ">\n";
  }

  public abstract String viewEntity(T t);

  protected abstract T parseEntity(final String str);

  @Override
  public void create(final T t) {
    RandomAccessFile file = getDataFile();
    try {
      file.seek(0);
      if ((t.getId() == null) || (t.getId() == 0L)) {
        t.setId(getNextId());
      }
      if (file.length() < (HEADER_XML.length() + TAIL_XML.length())) {
        file.write((HEADER_XML + "\n").getBytes());
      } else {
        file.seek(file.length() - (TAIL_XML.length()));
      }
      file.write(viewEntity(t).getBytes());
      file.write(("\n" + TAIL_XML).getBytes());

    } catch (IOException e) {
      LOG.log(Level.INFO, "create entity error", e);
    }
  }

  @Override
  public ArrayList<T> read() {
    RandomAccessFile file = getDataFile();
    ArrayList<T> list = new ArrayList<>();
    try {
      file.seek(0);
      String str = "";
      String myStr = "";
      while ((str = file.readLine()) != null) {
        if (HEADER_XML.contains(str) || OPEN_TAG.contains(str.trim())) continue;
        if (CLOSE_TAG.contains(str.trim())) {
          list.add(parseEntity(myStr));
          myStr = "";
        } else myStr += str.trim() + "|";
      }
    } catch (IOException e) {
      LOG.log(Level.INFO, "read entity error", e);
    }
    return list;
  }

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
      s += (end + 1 + TAIL_XML.length()) < file.length() ? "\n" : "\n";
      file.write(s.getBytes());
      file.write(buffer.getBytes());
      file.setLength(start + s.length() + buffer.length() - 1);
    } catch (IOException e) {
      LOG.log(Level.INFO, "update entity error", e);
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
      LOG.log(Level.INFO, "delete entity error", e);
    }
  }

  @Override
  public T getOneById(final long id) {
    RandomAccessFile file = getDataFile();
    String entityId = String.format(ID_TAG, id);
    String str;
    String myStr = "";
    T t = null;
    boolean found = false;
    try {
      while ((str = file.readLine()) != null) {
        if (found && CLOSE_TAG.contains(str.trim())) break;
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
      LOG.log(Level.INFO, "get entity by id error", e);
    }
    return t;
  }

  @Override
  protected long initMaxId() {
    long maxId = 0;
    RandomAccessFile file = getDataFile();
    try {
      file.seek(0);
      String str = "";
      while ((str = file.readLine()) != null) {
        if (str.contains("<id>")) {
          long id = Long.parseLong(str.replaceAll("\\</?id\\>", "").trim());
          if (maxId < id) maxId = id;
        }
      }
    } catch (IOException e) {
      LOG.log(Level.INFO, "error during initialization id", e);
    }
    return maxId;
  }

  public int[] getStartAndEndOfStr(final RandomAccessFile file, final T t) throws IOException {
    int[] arr = new int[2];
    int start = 0;
    int end = 0;
    String entityId = String.format(ID_TAG, t.getId());
    boolean found = false;
    String str = "";
    while ((str = file.readLine()) != null) {
      if (str.contains(entityId)) {
        found = true;
        start -= OPEN_TAG.length();
        arr[0] = start;
      }
      if (!found) {
        start += str.length() + 1;
      } else if (!CLOSE_TAG.contains(str.trim())) {
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
