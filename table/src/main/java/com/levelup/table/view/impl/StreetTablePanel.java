package com.levelup.table.view.impl;


import com.levelup.table.dao.DAO;
import com.levelup.table.entity.Street;
import com.levelup.table.dao.DataProvider;
import com.levelup.table.view.TablePanel;

/**
 * @author Veronika Kulichenko on 29.07.16.
 */
public class StreetTablePanel extends TablePanel<Street> {
    public StreetTablePanel(DAO<Street> streetDAO) {
        super(new StreetTableModelContainer(), streetDAO, new CreateStreetDialog());
    }
}
