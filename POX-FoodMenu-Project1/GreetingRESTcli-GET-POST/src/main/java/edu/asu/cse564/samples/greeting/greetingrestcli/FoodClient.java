/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.greeting.greetingrestcli;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FoodClient {

    private static final Logger LOG = LoggerFactory.getLogger(FoodClient.class);

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/GreetingRESTsrv-GET-POST/webapi";

    public FoodClient() {
        LOG.info("Creating a Greeting REST Client");
        
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("foodItem");
        System.out.println(BASE_URI);
    }

    public String postTextGreeting(String FoodItem) throws UniformInterfaceException {
        LOG.info("Send the foodItem "+ FoodItem +"to the FoodItemServer");
        ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(ClientResponse.class,FoodItem);
        return (response.getEntity(String.class));
    }


    public void close() {
        client.destroy();
    }
    
}
