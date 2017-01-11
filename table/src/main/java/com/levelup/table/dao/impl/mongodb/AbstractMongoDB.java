package com.levelup.table.dao.impl.mongodb;

import static com.mongodb.client.model.Filters.eq;

import com.levelup.table.dao.DAO;
import com.levelup.table.dao.dataproviders.MongoDBDataProvider;
import com.levelup.table.entity.Entity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;

/**
 * @author Veronika Kulichenko on 10.01.17.
 */
public abstract class AbstractMongoDB <T extends Entity> implements DAO<T> {

  private Long id;

  private final String collectionName;
  protected MongoDBDataProvider mongoDBDataProvider;

  public AbstractMongoDB(final MongoDBDataProvider mongoDBDataProvider, final String collectionName) {
    this.collectionName = collectionName;
    this.mongoDBDataProvider = mongoDBDataProvider;
  }

  public MongoDatabase getDB(){
    return mongoDBDataProvider.getDB();
  }

  @Override
  public void create(final T t) {
    MongoCollection<BasicDBObject> collection = getDB().getCollection(collectionName, BasicDBObject.class);
    collection.insertOne(viewEntity(t));
  }

  public abstract BasicDBObject viewEntity(T t);

  @Override
  public ArrayList<T> read() {
    ArrayList<T> list = new ArrayList<>();
    MongoCollection collection = getDB().getCollection(collectionName);
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

  @Override
  public void delete(final T t) {
    MongoCollection collection = getDB().getCollection(collectionName);
    collection.deleteOne(viewEntity(t));
  }

  @Override
  public T getOneById(final long id) {
    MongoCollection collection = getDB().getCollection(collectionName);
    BasicDBObject searchQuery = new BasicDBObject();
    searchQuery.put("id", id);
    FindIterable cursor = collection.find(searchQuery);
    while (cursor.iterator().hasNext()) {
      Document result = (Document) cursor.iterator().next();
      return parseEntity(result);
    }
    return null;
  }

  protected long getNextId() {
    if (null == id) id = initMaxId();
    return ++id;
  }

  protected long initMaxId(){
    Long maxId = null;
    MongoCollection collection = getDB().getCollection(collectionName);
    FindIterable findIterable = collection.find().sort(eq("id", -1)).limit(1);
    MongoCursor cursor = findIterable.iterator();
    if (cursor.hasNext()) {
      Document result = (Document) cursor.next();
      maxId = result.getLong("id");
    }
    return maxId == null ? 0 : maxId;
  }
}
