package com.levelup.table.dao.dataproviders;

import com.levelup.table.dao.DataProvider;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;

public class MongoDBDataProvider implements DataProvider {

  private MongoClient mongoClient;
  public MongoDatabase db;

  private String user;
  private String pass;
  private String database;
  private String host;
  private int port;

  private boolean credentialsUsed = false;

  public MongoDBDataProvider(final String host, final int port, String database) {
    this.host = host;
    this.port = port;
    this.database = database;
  }

  public MongoDBDataProvider(final String user, final String pass, final String database, final String host, final int port) {
    this.user = user;
    this.pass = pass;
    this.database = database;
    this.host = host;
    this.port = port;
    credentialsUsed = true;
  }

  @Override
  public void openConnection() {
    if(credentialsUsed) {
      mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(MongoCredential.createCredential(user, database, pass.toCharArray())));
    }else {
      mongoClient = new MongoClient(host, port);
    }
    db = mongoClient.getDatabase(database);
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
