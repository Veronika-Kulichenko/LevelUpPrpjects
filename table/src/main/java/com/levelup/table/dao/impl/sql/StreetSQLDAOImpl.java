package com.levelup.table.dao.impl.sql;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.SQLDataProvider;
import com.levelup.table.dao.mapper.StreetRowMapper;
import com.levelup.table.entity.Street;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StreetSQLDAOImpl implements DAO<Street> {

    private SQLDataProvider sqlDataProvider;

    public StreetSQLDAOImpl(SQLDataProvider sqlDataProvider) {
        this.sqlDataProvider = sqlDataProvider;
    }

    private static final String INSERT_STREET = "INSERT INTO STREET (STREET_NAME) VALUES(?)";

    private static final String SELECT_STREET = "SELECT * FROM STREET";
    private static final String DELETE_STREET = "DELETE FROM STREET WHERE ID = ?";
    private static final String UPDATE_STREET = "UPDATE STREET SET STREET_NAME = ? WHERE ID = ?";
    private static final String GET_STREET_BY_ID = "SELECT * FROM STREET WHERE ID = ?";


    @Override
    public void create(Street street) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(INSERT_STREET, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, street.getStreetName());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                street.setId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Street> read() {
        ArrayList<Street> result = new ArrayList<>();
        Statement statement = null;
        try {
            statement = sqlDataProvider.getStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_STREET);
            result = StreetRowMapper.fetch(resultSet);
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

    @Override
    public void update(Street street) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(UPDATE_STREET);
            preparedStatement.setString(1, street.getStreetName());
            preparedStatement.setLong(2, street.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void delete(Street street) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(DELETE_STREET);
            preparedStatement.setLong(1, street.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Street getOneById(long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = sqlDataProvider.getPreparedStatement(GET_STREET_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return new Street(
                        resultSet.getLong("ID"),
                        resultSet.getString("STREET_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    }

