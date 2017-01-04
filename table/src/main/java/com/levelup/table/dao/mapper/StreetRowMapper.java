package com.levelup.table.dao.mapper;

import com.levelup.table.entity.Citizen;
import com.levelup.table.entity.Street;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Veronika Kulichenko on 21.07.16.
 */
public class StreetRowMapper {

    public static ArrayList<Street> fetch(ResultSet resultSet) {
        ArrayList<Street> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                result.add(new Street(
                        resultSet.getLong("ID"),
                        resultSet.getString("STREET_NAME")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Street fetchOne(ResultSet resultSet) {
        Street result = null;
        try {
            if (resultSet.next()) {
                result = new Street(
                        resultSet.getLong("ID"),
                        resultSet.getString("STREET_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
