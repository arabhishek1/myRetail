package com.myRetail.resources;

import com.google.inject.Inject;
import com.myRetail.delegators.IProductDelegator;
import com.myRetail.entities.request.UpdatePriceRequest;
import com.myRetail.entities.response.GetProductAndPriceResponse;
import com.myRetail.entities.response.ProductResponse;
import com.myRetail.exceptions.EntityNotFoundException;
import com.myRetail.exceptions.MongoException;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by abhishek.ar on 15/06/17.
 */
@Path("/")
public class MyRetailResources {

    @Inject
    IProductDelegator productDelegator;

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductAndPriceInformation(@PathParam("id") int productId) throws EntityNotFoundException, RetryableException, NonRetryableException, IOException, MongoException {
        try {
            GetProductAndPriceResponse response = productDelegator.getProductDetails(productId);
            return Response.ok().entity(response).build();
        } catch (EntityNotFoundException ex){
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePriceInformaiton(@PathParam("id") int productId, UpdatePriceRequest updatePriceRequest) throws MongoException, NonRetryableException {
        try {
            productDelegator.updatePrice(updatePriceRequest);
            return Response.ok().build();
        } catch (NonRetryableException ex){
            return Response.status(428).entity(ex.getMessage()).build();
        } catch (Exception ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/product_name/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductName(@PathParam("id") int productId) throws EntityNotFoundException, MongoException {
        try {
            ProductResponse response = productDelegator.getProductNameById(productId);
            return Response.ok().entity(response).build();
        }catch (EntityNotFoundException ex){
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

}
