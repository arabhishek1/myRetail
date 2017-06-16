package com.myRetail.dao;

import com.myRetail.entities.db_entities.Product;
import com.myRetail.exceptions.MongoException;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public interface IProductDAO {

    Product getProductByProductId(int productId) throws MongoException;
}
