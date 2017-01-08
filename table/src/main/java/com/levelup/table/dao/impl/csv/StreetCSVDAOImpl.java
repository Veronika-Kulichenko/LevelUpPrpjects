package com.levelup.table.dao.impl.csv;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Street;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
public class StreetCSVDAOImpl extends AbstractCSVNDAO<Street> {
  public StreetCSVDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "street.csv", "id;streetName");
  }

  @Override
  public String viewEntity(final Street street) {
    return String.format("%d;%s", street.getId(), street.getStreetName());
  }

  @Override
  protected Street parseEntity(final String str) {
    String[] arr = str.split(";");
    return new Street(Long.parseLong(arr[0]), arr[1]);
  }
}
