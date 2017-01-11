package com.levelup.table.dao.impl.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.MongoDBDataProvider;
import com.levelup.table.dao.mapper.StreetRowMapper;
import com.levelup.table.entity.Entity;
import com.levelup.table.entity.Street;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * @author Veronika Kulichenko on 10.01.17.
 */
public abstract class AbstractMongoDB <T extends Entity> implements DAO<T> {

  private final String collectionName;
  protected final MongoDatabase database;


  public AbstractMongoDB(final MongoDBDataProvider mongoDBDataProvider, final String collectionName) {
    super();
    this.collectionName = collectionName;
    this.database = mongoDBDataProvider.getDB();
  }

  @Override
  public void create(final T t) {
    MongoCollection<BasicDBObject> collection = database.getCollection(collectionName, BasicDBObject.class);
    collection.insertOne(viewEntity(t));
  }

  public abstract BasicDBObject viewEntity(T t);

  @Override
  public ArrayList<T> read() {
    ArrayList<T> list = new ArrayList<>();
    MongoCollection collection = database.getCollection(collectionName);
    FindIterable findIterable = collection.find();
    MongoCursor cursor = findIterable.iterator();
    while (cursor.hasNext()) {
      Document result = (Document) cursor.next();
      T t = parseEntity(result);

      list.add(t);
    }

    return list;
  }

  protected abstract T parseEntity(final Document document);

//  @Override
//  public void update(final T t) {
//    MongoCollection collection = database.getCollection(collectionName);
//
//    collection.updateOne(eq("id", t.getId()), set("streetName", street.getStreetName()));
//
//  }

  @Override
  public void delete(final T t) {
    MongoCollection collection = database.getCollection(collectionName);
    collection.deleteOne(viewEntity(t));
  }

  @Override
  public T getOneById(final long id) {
    MongoCollection collection = database.getCollection(collectionName);
    BasicDBObject searchQuery = new BasicDBObject();
    searchQuery.put("id", id);
    FindIterable cursor = collection.find(searchQuery);
    while (cursor.iterator().hasNext()) {
      Document result = (Document) cursor.iterator().next();
      return parseEntity(result);
    }
    return null;
  }
}
