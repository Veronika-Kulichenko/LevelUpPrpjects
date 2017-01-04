package com.levelup.table;

import com.levelup.table.dao.dataproviders.FileDataProvider;
import com.levelup.table.dao.impl.csv.CitizenCSVDAOImpl;
import com.levelup.table.dao.impl.csv.StreetCSVDAOImpl;
import com.levelup.table.dao.impl.xml.StreetXMLDAOImpl;
import com.levelup.table.entity.Citizen;
import com.levelup.table.entity.Street;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Veronika Kulichenko on 01.01.17.
 */
public class StreetTest {

  public static void main(String[] args) throws FileNotFoundException {
//    StreetCSVDAOImpl streetCSVDAO = new StreetCSVDAOImpl(dataProvider);
//    streetCSVDAO.create(new Street(2l, "Korolenko"));
//    streetCSVDAO.create(new Street(3l, "Pishkina"));
//    streetCSVDAO.create(new Street(4l, "Shevchenka"));
//
//
//    ArrayList<Street> streets = streetCSVDAO.read();
//        for(Street street : streets){
//          System.out.println(street.getId() + " " + street.getStreetName());
//        }
//    streetCSVDAO.getOneById(4);
//    streetCSVDAO.delete(new Street(4l, ""));

//    streetCSVDAO.update(new Street(3l, "Gazety Pravdy"));
    StreetXMLDAOImpl streetXMLDAO = new StreetXMLDAOImpl(new FileDataProvider("street.xml"));
//    streetXMLDAO.create(new Street(3l, "Oujhy"));
//    streetXMLDAO.create(new Street(4l, "Ards"));
//    streetXMLDAO.delete(new Street(5l, ""));
    ArrayList<Street> streets = streetXMLDAO.read();
            for(Street street : streets){
      System.out.println(street.getId() + " " + street.getStreetName());
    }
//    streetXMLDAO.update(new Street(4l, "Moskovskaia"));
//streetXMLDAO.getOneById(4l);

  }
}
