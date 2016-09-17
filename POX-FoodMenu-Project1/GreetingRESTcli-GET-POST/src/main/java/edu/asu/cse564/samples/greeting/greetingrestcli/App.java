/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.greeting.greetingrestcli;

import com.sun.jersey.api.client.ClientResponse;

import edu.asu.cse564.samples.greeting.greetingrestcli.FoodClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
         
    public static void main( String[] args )
    {
    	String XMLinput = "<SelectedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">"
    +"<FoodItemId>303</FoodItemId>"
    +"<FoodItemId>156</FoodItemId></SelectedFoodItems>";
    	
        String XMLinput2 = "<NewFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">"
    +"<FoodItem country=\"GB\">"
        +"<name> Pasty</name>"
        +"<description>Tender cubes of steak, potatoes and swede wrapped in flakey short crust pastry.  Seasoned with lots of pepper.  Served with mashed potatoes, peas and a side of gravy</description>"
        +"<category>Dinner</category> <price>15.95</price> </FoodItem></NewFoodItems >";
        
        
        FoodClient greetingClient = new FoodClient();
        
        //pass the XMLInput String as Input 
      
        String result = greetingClient.postTextGreeting(XMLinput);
        
        LOG.info("result:--"+result);
        
        LOG.info("Ending Client application");
        
    }
    
}
