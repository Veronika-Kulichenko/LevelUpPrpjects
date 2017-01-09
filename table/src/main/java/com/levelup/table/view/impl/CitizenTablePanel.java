package com.levelup.table.view.impl;

import com.levelup.table.dao.DAO;
import com.levelup.table.entity.Citizen;
import com.levelup.table.view.TablePanel;

public class CitizenTablePanel extends TablePanel<Citizen> {

  public CitizenTablePanel(DAO<Citizen> citizenDAO) {
    super(new CitizenTableModelContainer(), citizenDAO, new CreateCitizenDialog());
    setName("Citizen Tab");
  }
}
