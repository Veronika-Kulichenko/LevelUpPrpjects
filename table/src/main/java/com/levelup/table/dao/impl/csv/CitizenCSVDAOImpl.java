package com.levelup.table.dao.impl.csv;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Citizen;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
public class CitizenCSVDAOImpl extends AbstractCSVNDAO<Citizen> {

  public CitizenCSVDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "citizen.csv", "id;firstName;lastName;age;streetId");
  }

  @Override
  public String viewEntity(final Citizen citizen) {
    return String.format("%d;%s;%s;%d;%d", citizen.getId(), citizen.getFirstName(), citizen.getLastName(), citizen.getAge(), citizen.getStreetId());
  }

  @Override
  protected Citizen parseEntity(final String str) {
    String[] arr = str.split(";");
    return new Citizen(Long.parseLong(arr[0]), arr[1], arr[2], Integer.parseInt(arr[3]), Long.parseLong(arr[4]));
  }
}
