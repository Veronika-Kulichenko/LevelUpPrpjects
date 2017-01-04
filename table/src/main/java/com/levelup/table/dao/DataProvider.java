package com.levelup.table.dao;

/**
 * Created by java on 30.07.2016.
 */
public interface DataProvider {

    enum ConnectionType {
        MYSQL, H2, MONGODB;
    }

    void openConnection();

    void closeConnection();
}
