package com.myRetail.dao.impl;

import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.myRetail.dao.IPriceDAO;
import com.myRetail.entities.db_entities.Price;
import com.myRetail.exceptions.MongoException;
import com.myRetail.util.Constants;
import org.bson.Document;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class PriceDAO implements IPriceDAO {

    private static final Logger logger = LoggerFactory.getLogger(PriceDAO.class);

    private MongoCollection<Document> priceCollections;
    private ObjectMapper mapper;

    @Inject
    public PriceDAO(MongoClient mongoClient, ObjectMapper mapper) {
        this.priceCollections = mongoClient.getDatabase(Constants.MY_RETAIL_DB).getCollection(Constants.PRICE_TABLE);
        this.mapper = mapper;
    }

    public Price getPriceByProductId(int productId) throws MongoException {
        try {
            Document document = new Document(Constants.ID, productId);
            List<Document> documents = priceCollections.find(document).into(new ArrayList<Document>());
            Price price = null;
            for (Document doc : documents) {
                price = mapper.convertValue(doc, Price.class);
            }
            return price;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new MongoException("Error in Fetching Document in Mongo " + ex.getMessage());
        }
    }


    public void updatePrice(Price newPrice) throws MongoException {
        try {
            Price oldPrice = getPriceByProductId(newPrice.getProductId());
            Document newDocument = mapper.convertValue(newPrice, Document.class);
            Document oldDocument = new Document();
            oldDocument.put(Constants.ID, oldPrice.getProductId());
            priceCollections.replaceOne(oldDocument, newDocument);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new MongoException("Error in Updating Document in Mongo " + ex.getMessage());
        }
    }
}
