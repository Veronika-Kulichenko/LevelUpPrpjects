package com.levelup.table.dao.impl.mongodb;

import com.levelup.table.dao.dataproviders.MongoDBDataProvider;
import com.levelup.table.entity.Citizen;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @author Veronika Kulichenko on 10.01.17.
 */
public class CitizenMongoDBDAOImpl extends AbstractMongoDB<Citizen> {

  private static final String collectionName = "citizen";

  public CitizenMongoDBDAOImpl(final MongoDBDataProvider mongoDBDataProvider) {
    super(mongoDBDataProvider, collectionName);
  }

  @Override
  public void update(final Citizen citizen) {
    MongoCollection collection = getDB().getCollection(collectionName);
    BasicDBObject old = new BasicDBObject();
    old.put("id", citizen.getId());
    BasicDBObject newDocument = new BasicDBObject();
    newDocument.put("firstName", citizen.getFirstName());
    newDocument.put("lastName", citizen.getLastName());
    newDocument.put("age", citizen.getAge());
    newDocument.put("streetId", citizen.getStreetId());
    BasicDBObject updated = new BasicDBObject();
    updated.put("$set", newDocument);
    collection.updateMany(old, updated);
  }

  @Override
  public BasicDBObject viewEntity(final Citizen citizen) {
    BasicDBObject document = new BasicDBObject();
    if ((citizen.getId() == null) || (citizen.getId() == 0L)) {
      citizen.setId(getNextId());
    }
    document.put("id", citizen.getId());
    document.put("firstName", citizen.getFirstName());
    document.put("lastName", citizen.getLastName());
    document.put("age", citizen.getAge());
    document.put("streetId", citizen.getStreetId());
    return document;
  }

  @Override
  protected Citizen parseEntity(final Document document) {
    Citizen citizen = new Citizen();
    citizen.setId(document.getLong("id"));
    citizen.setFirstName(document.getString("firstName"));
    citizen.setLastName(document.getString("lastName"));
    citizen.setAge(document.getInteger("age"));
    citizen.setStreetId(document.getLong("streetId"));
    return citizen;
  }
}
