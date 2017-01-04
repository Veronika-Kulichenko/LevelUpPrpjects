package com.levelup.table.dao.impl.xml;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.impl.json.StreetJsonDAOImpl;
import com.levelup.table.entity.Street;
import java.util.logging.Logger;

/**
 * @author Veronika Kulichenko on 03.01.17.
 */
public class StreetXMLDAOImpl extends AbstractXMLDAO<Street> {

  public StreetXMLDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "street_list", "street");
  }

  @Override
  public String viewEntity(final Street street) {
    return String.format("\t<street>\n\t\t<id>%d</id>\n\t\t<streetName>%s</streetName>\n\t</street>", street.getId(), street.getStreetName());
  }

  @Override
  protected Street parseEntity(final String str) {
    String[] arr = str.replaceAll("\\t|\\n", "").replaceAll("\\</?\\w+\\>", "").split("\\|");
    return new Street(Long.parseLong(arr[0]), arr[1]);
  }

}
