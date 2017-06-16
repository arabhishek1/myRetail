package com.myRetail.dao;

import com.myRetail.entities.db_entities.Price;
import com.myRetail.exceptions.MongoException;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public interface IPriceDAO {

    Price getPriceByProductId(int productId) throws MongoException;

    void updatePrice(Price newPrice) throws MongoException;

}
