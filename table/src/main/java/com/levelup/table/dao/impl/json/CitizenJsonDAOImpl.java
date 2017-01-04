package com.levelup.table.dao.impl.json;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Citizen;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
public class CitizenJsonDAOImpl extends AbstractJSONDAO<Citizen> {
  public CitizenJsonDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "citizenList");
  }

  @Override
  public String viewEntity(final Citizen citizen) {
    return String.format("{id:%d, firstName:%s, lastName:%s, age:%d, streetId:%d}", citizen.getId(), citizen.getFirstName(), citizen.getLastName(), citizen.getAge(), citizen.getStreetId());
  }

  @Override
  protected Citizen parseEntity(final String str) {
    String[] arr = str.replace("{id:", "").replace(" firstName:", "").replace(" lastName:", "").replace(" age:", "").replace(" streetId:", "").replace("}", "").split(",");
    return new Citizen(Long.parseLong(arr[0]), arr[1], arr[2], Integer.parseInt(arr[3]), Long.parseLong(arr[4]));
  }
}
