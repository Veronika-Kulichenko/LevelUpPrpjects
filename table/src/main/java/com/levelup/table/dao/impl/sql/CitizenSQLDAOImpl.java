package com.levelup.table.dao.impl.sql;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.SQLDataProvider;
import com.levelup.table.dao.mapper.CitizenRowMapper;
import com.levelup.table.entity.Citizen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CitizenSQLDAOImpl implements DAO<Citizen> {

    private static final String INSERT_CITIZEN = "INSERT INTO CITIZEN (FIRST_NAME, LAST_NAME, AGE, STREET_ID) VALUES(?, ?, ?, ?)";
    private static final String SELECT_CITIZEN = "SELECT * FROM CITIZEN";
    private static final String UPDATE_CITIZEN = "UPDATE CITIZEN SET FIRST_NAME = ?, LAST_NAME = ?, AGE = ?, STREET_ID = ? WHERE ID = ?";
    private static final String DELETE_CITIZEN = "DELETE FROM CITIZEN WHERE ID = ?";
    private static final String GET_ONE_BY_ID = "SELECT * FROM CITIZEN WHERE ID = ?";

    private SQLDataProvider sqlDataProvider;

    public CitizenSQLDAOImpl(SQLDataProvider sqlDataProvider) {
        this.sqlDataProvider = sqlDataProvider;
    }

    @Override
    public void create(Citizen citizen) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(INSERT_CITIZEN, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, citizen.getFirstName());
            preparedStatement.setString(2, citizen.getLastName());
            preparedStatement.setInt(3, citizen.getAge());
            preparedStatement.setObject(4, citizen.getStreetId());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                citizen.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ArrayList<Citizen> read() {
        ArrayList<Citizen> result = new ArrayList<>();
        Statement statement = null;
        try {
            statement = sqlDataProvider.getStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CITIZEN);
            result = CitizenRowMapper.fetch(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

//    public ArrayList<Citizen> getCitizenByAge(int age) {
//        ArrayList<Citizen> result = new ArrayList<>();
//
//        PreparedStatement statement = getPreparedStatement("SELECT * FROM CITIZEN WHERE AGE = ?");
//
//        statement.setInt(1, age);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        result = CitizenRowMapper.fetch(resultSet);
//        return result;
//    }

    @Override
    public Citizen getOneById(long id) {
        Citizen result = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(GET_ONE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = CitizenRowMapper.fetchOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public void update(Citizen citizen) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(UPDATE_CITIZEN);

            preparedStatement.setString(1, citizen.getFirstName());
            preparedStatement.setString(2, citizen.getLastName());
            preparedStatement.setInt(3, citizen.getAge());
            preparedStatement.setObject(4, citizen.getStreetId());
            preparedStatement.setLong(5, citizen.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Citizen citizen) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(DELETE_CITIZEN);
            preparedStatement.setLong(1, citizen.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
