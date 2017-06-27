package com.myRetail.dao.impl;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.myRetail.dao.ShipmentWeightHistoryDAO;
import com.myRetail.entities.db_entities.*;
import com.myRetail.exceptions.CartmanException;
import com.myRetail.sao.AbstractSAO;
import com.myRetail.sao.MyRetailSAO;
import com.myRetail.util.Constants;
import com.myRetail.util.MongoHelper;
import org.bson.Document;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public class MongoShipmentWeightHistoryDAO implements ShipmentWeightHistoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(MongoShipmentWeightHistoryDAO.class);
    private MongoCollection<Document> shipmentWeightHistoryCollections;
    private ObjectMapper mapper;
    private MongoHelper mongoHelper;
    private MetricRegistry metricRegistry;

    @Inject
    MyRetailSAO myRetailSAO;

    @Inject
    public MongoShipmentWeightHistoryDAO(MongoClient mongoClient, MongoHelper mongoHelper,
                                         ObjectMapper mapper, MetricRegistry metricRegistry){
        this.mongoHelper = mongoHelper;
        this.mapper = mapper;
        shipmentWeightHistoryCollections = mongoClient.getDatabase(Constants.CARTMAN_DB).getCollection(Constants.SHIPMENT_WEIGHT_HISTORY_TABLE);
        this.metricRegistry = metricRegistry;
    }

    @Override
    public List<ShipmentWeightHistory> getShipmentHistories(String merchantRefId) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.get_shipment_weight_history").time();
        try {
            Document document = new Document(Constants.MERCHANT_REFERENCE_ID, merchantRefId);
            List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
            List<ShipmentWeightHistory> shipmentWeightHistories = new ArrayList<>();
            for (Document doc : documents) {
                shipmentWeightHistories.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
            }
            return shipmentWeightHistories;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }


    private void checkSWH(String merchantRefId){
        Document document = new Document(Constants.MERCHANT_REFERENCE_ID, merchantRefId).append(Constants.WEIGHT_SOURCE, WeightSource.SELLER_PACKING_DETAILS);
        List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
        List<ShipmentWeightHistory> shipmentWeightHistories = new ArrayList<>();
        for (Document doc : documents) {
            shipmentWeightHistories.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
        }
        String RemovabledocumentId = "";
        for(ShipmentWeightHistory shipmentWeightHistory : shipmentWeightHistories){

        }
    }

    private void deleteSWH(Set<String> removeIds){
        for(String swhId : removeIds){
            Document document = new Document(Constants.ID, swhId);
            shipmentWeightHistoryCollections.deleteOne(document);
//            List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
//            List<ShipmentWeightHistory> shipmentWeightHistories = new ArrayList<>();
//            int i=0;
//            for (Document doc : documents) {
//                shipmentWeightHistories.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
//                System.out.println(shipmentWeightHistories.get(i).toString());
//                ++i;
//            }
        }
        System.out.println("Removal of "+ removeIds.size() +" swh complete");
    }



    @Override
    public void removeSWH(String fileName) throws CartmanException {
        try {
            Map<String, Weight> actualSellerWeightMap = new HashMap<>();
            readFileAndPopulateMap(actualSellerWeightMap, fileName);
            System.out.println(actualSellerWeightMap.size());
            Set<String> removeIds = new HashSet<>();

            for(Map.Entry entry : actualSellerWeightMap.entrySet()) {

                Document document = new Document(Constants.MERCHANT_REFERENCE_ID, entry.getKey().toString()).append(Constants.WEIGHT_SOURCE, WeightSource.SELLER_PACKING_DETAILS.getName());
                List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
                List<ShipmentWeightHistory> shipmentWeightHistories = new ArrayList<>();
                for (Document doc : documents) {
                    shipmentWeightHistories.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
                }

                if ( !shipmentWeightHistories.isEmpty() && shipmentWeightHistories.size() > 1) {
                    Weight correctWeight = (Weight) entry.getValue();
                    for (ShipmentWeightHistory shipmentWeightHistory : shipmentWeightHistories) {
                        Weight currentWeight = shipmentWeightHistory.getWeight();
                        if (Math.abs(correctWeight.getDeadWeight().getWeight() - currentWeight.getDeadWeight().getWeight()) <= 0.1 &&
                                Math.abs(correctWeight.getVolume().getL() - currentWeight.getVolume().getL()) <= 0.1 &&
                                Math.abs(correctWeight.getVolume().getB() - currentWeight.getVolume().getB()) <= 0.1 &&
                                Math.abs(correctWeight.getVolume().getH() - currentWeight.getVolume().getH()) <= 0.1){
//                            System.out.println("correct weight " + shipmentWeightHistory.getShardKey());
                        } else {
//                            System.out.println(shipmentWeightHistory.getShardKey());
                            removeIds.add(shipmentWeightHistory.getShardKey());
                        }
                    }
                } else {
//                    System.out.println(shipmentWeightHistories.get(0).getMerchantRefId());
                }
            }
            System.out.println(removeIds.size());
            deleteSWH(removeIds);
//            List<String> merchantRefIds = new ArrayList<>();
//            merchantRefIds.addAll(actualSellerWeightMap.keySet());
//            myRetailSAO.recalibrateWeight(merchantRefIds);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        }
    }

    private void readFileAndPopulateMap(Map<String, Weight> actualSellerWeightMap, String fileName) {
//        String csvFile = "/Users/abhishek.ar/Downloads/Untitled spreadsheet - Sheet1.csv";
//        String csvFile = "/Users/abhishek.ar/Downloads/test.csv";
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        System.out.println(fileName);

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] csv = line.split(cvsSplitBy);

                Weight weight = new Weight();
                weight.setDeadWeight(new DeadWeight(Double.parseDouble(csv[5])));
                weight.setVolume(new Volume(Double.parseDouble(csv[1]), Double.parseDouble(csv[2]), Double.parseDouble(csv[3])));
                actualSellerWeightMap.put(csv[0], weight);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public List<ShipmentWeightHistory> getShipmentWeightHistory(String merchantRefId, WeightSource weightSource) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.get_shipment_weight_history_for_source").time();
        try {
            Document document = new Document(Constants.MERCHANT_REFERENCE_ID, merchantRefId).append(Constants.WEIGHT_SOURCE, weightSource.getName());
            List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
            List<ShipmentWeightHistory> shipmentWeightHistory = new ArrayList<>();
            for (Document doc : documents) {
                shipmentWeightHistory.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
            }
            return shipmentWeightHistory;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }

    @Override
    public List<ShipmentWeightHistory> getShipmentHistories(List<String> merchantRefIds) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.get_shipment_weight_history_batch").time();
        try {
            Document document = new Document(Constants.MERCHANT_REFERENCE_ID, new Document(Constants.MONGO_IN, merchantRefIds));
            List<Document> documents = shipmentWeightHistoryCollections.find(document).into(new ArrayList<>());
            List<ShipmentWeightHistory> shipmentWeightHistories = new ArrayList<>();
            for (Document doc : documents) {
                shipmentWeightHistories.add(mapper.convertValue(doc, ShipmentWeightHistory.class));
            }
            return shipmentWeightHistories;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }

    @Override
    public void saveShipmentWeightHistory(ShipmentWeightHistory shipmentWeightHistory) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.save_shipment_weight_history").time();
        try {
            shipmentWeightHistory.setShardKey(mongoHelper.getNewHashKey(shipmentWeightHistory.getMerchantRefId()));
            shipmentWeightHistory.setCreatedAt(new DateTime());
            shipmentWeightHistory.setUpdatedAt(new DateTime());
            Document document = mapper.convertValue(shipmentWeightHistory, Document.class);
            document.append(Constants.TTL_DATE, new Date());
            shipmentWeightHistoryCollections.insertOne(document);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }

    @Override
    public void saveShipmentWeightHistory(List<ShipmentWeightHistory> shipmentWeightHistories) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.save_shipment_weight_history_batch").time();
        try {
            List<Document> mongoDocs = new ArrayList<>();
            for (ShipmentWeightHistory shipmentWeightHistory : shipmentWeightHistories) {
                shipmentWeightHistory.setShardKey(mongoHelper.getNewHashKey(shipmentWeightHistory.getMerchantRefId()));
                shipmentWeightHistory.setUpdatedAt(new DateTime());
                shipmentWeightHistory.setCreatedAt(new DateTime());
                Document document = mapper.convertValue(shipmentWeightHistory, Document.class);
                document.append(Constants.TTL_DATE, new Date());
                mongoDocs.add(document);
            }
            shipmentWeightHistoryCollections.insertMany(mongoDocs);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }

    @Override
    public void updateShipmentWeightHistory(ShipmentWeightHistory newShipmentWeightHistory) throws CartmanException {
        Timer.Context timer = metricRegistry.timer("mongo.update_shipment_weight_history").time();
        try {
            List<ShipmentWeightHistory> oldShipmentWeightHistories = getShipmentWeightHistory(newShipmentWeightHistory.getMerchantRefId(), newShipmentWeightHistory.getWeightSource());
            for(ShipmentWeightHistory weightHistory : oldShipmentWeightHistories ) {
                newShipmentWeightHistory.setShardKey(weightHistory.getShardKey());
                newShipmentWeightHistory.setUpdatedAt(new DateTime());
                Document newDocument = mapper.convertValue(newShipmentWeightHistory, Document.class);
                Document updateDoc = new Document(Constants.MONGO_SET, newDocument);
                Document oldDocument = new Document(Constants.DOCUMENT_ID, weightHistory.getShardKey());
                shipmentWeightHistoryCollections.updateOne(oldDocument, updateDoc);
            }
        }catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CartmanException(500, CartmanErrorCode.MONGO_ERROR.getName(), ex);
        } finally {
            timer.stop();
            timer.close();
        }
    }

}

