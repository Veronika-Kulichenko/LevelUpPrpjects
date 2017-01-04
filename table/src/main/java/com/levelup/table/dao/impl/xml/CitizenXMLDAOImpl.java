package com.levelup.table.dao.impl.xml;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.entity.Citizen;

/**
 * @author Veronika Kulichenko on 04.01.17.
 */
public class CitizenXMLDAOImpl extends AbstractXMLDAO<Citizen> {

  public CitizenXMLDAOImpl(final FileDataProvider fileDataProvider) {
    super(fileDataProvider, "citizen_list", "citizen");
  }

  @Override
  public String viewEntity(final Citizen citizen) {
    return String.format("\t<citizen>\n\t\t<id>%d</id>\n\t\t<firstName>%s</firstName>\n\t\t<lastName>%s</lastName>\n\t\t<age>%d</age>\n\t\t"
                         + "<streetId>%d</streetId>\n\t</citizen>", citizen.getId(), citizen.getFirstName(), citizen.getLastName(), citizen.getAge(), citizen.getStreetId());
  }

  @Override
  protected Citizen parseEntity(final String str) {
    String[] arr = str.replaceAll("\\t|\\n", "").replaceAll("\\</?\\w+\\>", "").split("\\|");
    return new Citizen(Long.parseLong(arr[0]), arr[1], arr[2], Integer.parseInt(arr[3]), Long.parseLong(arr[4]));
  }
}
