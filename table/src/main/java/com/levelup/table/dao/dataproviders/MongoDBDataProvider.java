package com.levelup.table.dao.dataproviders;

import com.levelup.table.dao.DataProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.Statement;

public class MongoDBDataProvider implements DataProvider {

  private MongoClient mongoClient;
  public MongoDatabase db;

  private final String url;
  private final String user;
  private final String pass;
  private final String database;


  public MongoDBDataProvider(String url, String user, String pass, String database) {
    this.url = url;
    this.user = user;
    this.pass = pass;
    this.database = database;
  }

  @Override
  public void openConnection() {
    mongoClient = new MongoClient("localhost", 27017);
    db = mongoClient.getDatabase("address-book");
  }

  @Override
  public void closeConnection() {
    mongoClient.close();
    mongoClient = null;
    db = null;
  }

  public MongoDatabase getDB(){
    return db;
  }
}
