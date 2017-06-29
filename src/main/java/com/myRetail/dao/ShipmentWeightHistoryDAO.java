package com.myRetail.dao;

import com.myRetail.entities.db_entities.ShipmentWeightHistory;
import com.myRetail.entities.db_entities.WeightSource;
import com.myRetail.exceptions.CartmanException;

import java.util.List;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public interface ShipmentWeightHistoryDAO {
    List<ShipmentWeightHistory> getShipmentHistories(String merchantRefId) throws CartmanException;

    List<ShipmentWeightHistory> getShipmentWeightHistory(String merchantRefId, WeightSource weightSource) throws CartmanException;

    List<ShipmentWeightHistory> getShipmentHistories(List<String> merchantRefId) throws CartmanException;

    void saveShipmentWeightHistory(ShipmentWeightHistory shipmentWeightHistory) throws CartmanException;

    void saveShipmentWeightHistory(List<ShipmentWeightHistory> shipmentWeightHistories) throws CartmanException;

    void updateShipmentWeightHistory(ShipmentWeightHistory newShipmentWeightHistory) throws CartmanException;

    void removeSWH(String fileName) throws CartmanException;

    void readSWHAndDeleteNewSWHForSFS(String fileName);
}
