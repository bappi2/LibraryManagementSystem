package com.mmefta.lms.mongo;

import com.google.common.collect.Lists;
import com.mmefta.lms.configuration.MongoConfiguration;
import com.mmefta.lms.util.ObjectMapperUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://www.mongodb.com/developer/languages/java/java-setup-crud-operations/

public class MongoManager {
    private MongoClient mongoClient;
    private final String dbName;
    private final String authorsCollection;
    private final String bookItemCollection;
    private final String bookListCollection;

    private final Pattern patternIsoDate = Pattern.compile("ISODate\\(\"([^\"]+)\"\\)");
    private final Pattern patternNumberLong = Pattern.compile("NumberLong\\(\"*(\\d+)\"*\\)");
    private final Pattern patternObjectId = Pattern.compile("ObjectId\\(\"([^\"]+)\"\\)");


    public MongoManager(MongoClient mongoClient, MongoConfiguration mongoConfiguration) {
        this.mongoClient = mongoClient;
        this.dbName = mongoConfiguration.getDbName();
        this.authorsCollection = mongoConfiguration.getAuthorsCollection();
        this.bookItemCollection = mongoConfiguration.getBookItemCollection();
        this.bookListCollection = mongoConfiguration.getBookCollection();
    }

    public <T> List<T> getAll(Class<T> tClass, String collection, Map<String, Object> query) {
        BasicDBObject queryObject = new BasicDBObject(query);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        FindIterable<Document> documents = mongoCollection.find(queryObject);
        List<T> results = getResultDocument(tClass, collection, documents);
        return results;
    }

    public <T> List<T> getResultDocument(Class<T> tClass, String collection, FindIterable<Document> documents) {
        List<T> results = Lists.newArrayList();
        documents.forEach((Block<Document>) d -> {
            try {
                results.add(ObjectMapperUtil.deserialize(bsonToJson(d), tClass));
            } catch (IOException e) {
                //logger.error("getAll failed to deserialize for " + collection + " " + ExceptionUtilsEx.getExceptionInformation(e));
            }
        });
        return results;
    }

    private String bsonToJson(Document document) {
        if (document == null) {
            return null;
        }

        String jsonString = document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.SHELL).build());
        Matcher m = patternNumberLong.matcher(jsonString);

        if (m.find()) {
            jsonString = m.replaceAll("$1");
        }

        Matcher m2 = patternIsoDate.matcher(jsonString);
        if (m2.find()) {
            jsonString = m2.replaceAll("\"$1\"");
        }

        Matcher m3 = patternObjectId.matcher(jsonString);
        if (m3.find()) {
            jsonString = m3.replaceAll("\"$1\"");
        }

        return jsonString;
    }

    public long count(String collection, Map<String, Object> query) {
        BasicDBObject queryObject = new BasicDBObject(query);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        return mongoCollection.countDocuments(queryObject);
    }

    public void insertAll(String collection, List<String> documents) throws Exception {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        List<Document> documentsToPatch = new ArrayList<>();
        for (String json : documents) {
            Document document = Document.parse(json);
            documentsToPatch.add(document);
        }
        mongoCollection.insertMany(documentsToPatch);
    }

    public UpdateResult getUpdateReplaceResult(String collection, Map<String, Object> query, String json, Boolean update, Boolean updateOneNote) {
        Document document = Document.parse(json);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        Document filter = new Document();
        query.forEach(filter::put);
        UpdateOptions upsertOption = (new UpdateOptions()).upsert(true);
        UpdateResult vendorUpdateResult;
        if (update) {
            if(updateOneNote) {
                vendorUpdateResult = mongoCollection.updateOne(filter, document, upsertOption);
            } else {
                vendorUpdateResult = mongoCollection.updateMany(filter, document, upsertOption);
            }
        }
        else {
            vendorUpdateResult = mongoCollection.replaceOne(filter, document, upsertOption);
        }
        return vendorUpdateResult;
    }

    public boolean delete(String collection, Map<String, Object> query) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        Document filter = new Document();
        query.forEach(filter::put);
        DeleteResult deleteResult = mongoCollection.deleteOne(filter);
        return deleteResult.wasAcknowledged();
    }

}
