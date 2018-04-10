package com.etsi.tit.c2c.tablon;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by josel on 19/02/2018.
 */

public class MongoClass {

    public MongoDatabase createConnection() {
        MongoClientURI uri  = new MongoClientURI("mongodb://c2c:titc2capp@ds243418.mlab.com:43418/socialdb");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        return db;
    }
}
