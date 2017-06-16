package com.myRetail.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.myRetail.dao.IProductDAO;
import com.myRetail.entities.db_entities.Product;
import com.myRetail.exceptions.MongoException;
import com.myRetail.util.Constants;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class ProductDAO implements IProductDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    private MongoCollection<Document> productCollections;
    private ObjectMapper mapper;

    @Inject
    public ProductDAO(MongoClient mongoClient, ObjectMapper mapper) {
        this.productCollections = mongoClient.getDatabase(Constants.MY_RETAIL_DB).getCollection(Constants.PRODUCT_TABLE);
        this.mapper = mapper;
    }

    public Product getProductByProductId(int productId) throws MongoException {
        try {
            Document document = new Document(Constants.ID, productId);
            List<Document> documents = productCollections.find(document).into(new ArrayList<Document>());
            Product product = null;
            for (Document doc : documents) {
                product = mapper.convertValue(doc, Product.class);
            }
            return product;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new MongoException("Error in Fetching Document in Mongo " + ex.getMessage());
        }
    }
}
