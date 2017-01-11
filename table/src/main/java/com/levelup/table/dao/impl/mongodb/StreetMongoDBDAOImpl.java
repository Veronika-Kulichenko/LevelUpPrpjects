package com.levelup.table.dao.impl.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.levelup.table.dao.dataproviders.MongoDBDataProvider;
import com.levelup.table.entity.Street;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @author Veronika Kulichenko on 10.01.17.
 */
public class StreetMongoDBDAOImpl extends AbstractMongoDB<Street> {

  private static final String collectionName = "street";

  public StreetMongoDBDAOImpl(final MongoDBDataProvider mongoDBDataProvider) {
    super(mongoDBDataProvider, collectionName);
  }

  @Override
  public BasicDBObject viewEntity(final Street street) {
    BasicDBObject document = new BasicDBObject();
    document.put("id", street.getId());
    document.put("streetName", street.getStreetName());
    return document;
  }

  @Override
  protected Street parseEntity(final Document document) {
    Street street = new Street();
    street.setId(document.getLong("id"));
    street.setStreetName(document.getString("streetName"));
    return street;
  }

  @Override
  public void update(final Street street) {
    MongoCollection collection = database.getCollection(collectionName);
    collection.updateOne(eq("id", street.getId()), set("streetName", street.getStreetName()));
    //    BasicDBObject old = new BasicDBObject();
    //    old.put("id", street.getId());
    //
    //    BasicDBObject newDocument = new BasicDBObject();
    //    newDocument.put("streetName", street.getStreetName());
    //
    //    BasicDBObject updated = new BasicDBObject();
    //    updated.put("$set", newDocument);
    //
    //    collection.updateOne(old, updated);
  }

}
