package com.levelup.table.dao;

/**
 * @author Veronika Kulichenko on 30.07.2016.
 */
public interface DataProvider {

    enum ConnectionType {
        MYSQL, H2, MONGODB, CSV, JSON, XML
    }

    void openConnection();

    void closeConnection();
}
