package com.levelup.table.view.impl;

import com.levelup.table.entity.Street;
import com.levelup.table.view.TableModelContainer;

import java.util.List;

/**
 * @author Veronika Kulichenko on 29.07.16.
 */
public class StreetTableModelContainer extends TableModelContainer<Street> {

    public StreetTableModelContainer() {
        super(new String[]{"ID", "Street Name"});
    }

    public StreetTableModelContainer(List<Street> data) {
        super(new String[]{"ID", "Street Name" }, data);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Street street = data.get(rowIndex);
        if(columnIndex == 1) {
            return street.getStreetName();
        } return street.getId();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Street street = data.get(rowIndex);
        String updatedValue = String.valueOf(aValue);
        if(columnIndex == 1) {
            street.setStreetName(updatedValue);
        getUpdated().add(street);
    }

    }
}
