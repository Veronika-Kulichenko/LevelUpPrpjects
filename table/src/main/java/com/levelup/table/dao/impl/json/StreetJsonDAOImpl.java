package com.levelup.table.dao.impl.json;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Street;

/**
 * @author Veronika Kulichenko on 01.01.17.
 */
public class StreetJsonDAOImpl extends AbstractJSONDAO<Street> {

  public StreetJsonDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "street.json", "streetList");
  }

  @Override
  public String viewEntity(final Street street) {
    return String.format("{id:%d, streetName:%s}", street.getId(), street.getStreetName());
  }

  @Override
  protected Street parseEntity(final String str) {
    String[] arr = str.replace("{id:", "").replace(" streetName:", "").replace("}", "").split(",");
    return new Street(Long.parseLong(arr[0]), arr[1]);
  }
}
