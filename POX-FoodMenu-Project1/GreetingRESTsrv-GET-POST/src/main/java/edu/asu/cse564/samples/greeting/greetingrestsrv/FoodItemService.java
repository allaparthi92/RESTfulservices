package edu.asu.cse564.samples.greeting.greetingrestsrv;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.asu.cse564.samples.greeting.greetingrestsrv.FoodItem;

public class FoodItemService {
	private static final Logger LOG = LoggerFactory
			.getLogger(FoodResource.class);
	 static List<FoodItem> ExistingList = FoodItemServiceXMLFile();

	public List<FoodItem> FoodItemServiceXMLString(String XMLstring) {

		InputSource XMLinput = new InputSource(new StringReader(
				XMLstring.toString()));
		List<FoodItem> AddList = new ArrayList<FoodItem>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XMLinput);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("FoodItem");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String contry = eElement.getAttribute("country");

					String nameval = eElement.getElementsByTagName("name")
							.item(0).getTextContent();
					String des = eElement.getElementsByTagName("description")
							.item(0).getTextContent();
					String categotyval = eElement
							.getElementsByTagName("category").item(0)
							.getTextContent();
					Double Priceval = Double.parseDouble(eElement
							.getElementsByTagName("price").item(0)
							.getTextContent());
					AddList.add(new FoodItem(contry, -1, nameval, des,
							categotyval, Priceval));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AddList;
	}

	public static List<FoodItem> FoodItemServiceXMLFile() {

		if(ExistingList == null){
			ExistingList = new ArrayList<FoodItem>();
		}
		ClassLoader classLoader = FoodItemService.class.getClassLoader();
		InputStream resourceAsStream = classLoader
				.getResourceAsStream("FoodItemData.xml");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(resourceAsStream);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("FoodItem");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String contry = eElement.getAttribute("country");
					Integer IdVal = Integer.parseInt(eElement
							.getElementsByTagName("id").item(0)
							.getTextContent());
					String nameval = eElement.getElementsByTagName("name")
							.item(0).getTextContent();
					String des = eElement.getElementsByTagName("description")
							.item(0).getTextContent();
					String categotyval = eElement
							.getElementsByTagName("category").item(0)
							.getTextContent();
					Double Priceval = Double.parseDouble(eElement
							.getElementsByTagName("price").item(0)
							.getTextContent());
					ExistingList.add(new FoodItem(contry, IdVal, nameval, des,
							categotyval, Priceval));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ExistingList;
	}

	public List FoodItemServiceXMLStringRetrieve(String XMLstring) {

		InputSource XMLinput = new InputSource(new StringReader(
				XMLstring.toString()));
		List<String> AddList = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XMLinput);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("FoodItemId");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					AddList.add(eElement.getTextContent());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return AddList;
	}
	
	
	public String RetreivedListXML(List<String> RetrieveList) throws ParserConfigurationException{
		
		
		StringBuffer result= new StringBuffer() ;
		result.append("<RetrievedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">");
		
    	for(int i=0;i<RetrieveList.size();i++){
    		int flag =0;
    		int Id = Integer.parseInt(RetrieveList.get(i));
    		for(FoodItem x : ExistingList){
    			if(x.getId()==Id){
    				result.append("<FoodItem country=\""+x.getCountry()+"\">");	
    				result.append("<id>"+x.getId()+"</id>");
    				result.append("<name>"+x.getName()+"</name>");
    				result.append("<description>"+x.getDescription()+"</description>");
    				result.append("<category>"+x.getCategory()+"</category>");
    				result.append("<price>"+x.getPrice()+"</price>");
    				result.append("</FoodItem>");
    				flag = 1;
    			}			
    		}
    		if(flag == 1)
    			continue;
    		else{
    			result.append("<InvalidFoodItem>");
    			
    			result.append("<foodItem>"+Id+"</foodItem>");
    			
    			result.append("</InvalidFoodItem>");
    		
    		}
    	}
    	result.append("</RetrievedFoodItems>");
    	return result.toString();
	}

	public String addToExistingXml(List<FoodItem> AddList) throws Exception,
			TransformerFactoryConfigurationError {
		
		Integer max = 0;
		for (FoodItem x : ExistingList) {
			if (x.getCategory().equals(AddList.get(0).getCategory())
					&& x.getName().equals(AddList.get(0).getName())) {
				String responseContent = "<FoodItemExists xmlns=”http://cse564.asu.edu/PoxAssignment”>"
						+ "<FoodItemId>"
						+ x.getId()
						+ "</FoodItemId></FoodItemExists>";

				return responseContent;
			}
			max = Math.max(max, x.getId());
		}

		max = max + 1;
		ExistingList.add(new FoodItem(AddList.get(0).getCountry(), max, AddList
				.get(0).getName(), AddList.get(0).getDescription(), AddList
				.get(0).getCategory(), AddList.get(0).getPrice()));
		String responseContent = "<FoodItemAdded xmlns=\"http://cse564.asu.edu/PoxAssignment\">"
				+ "<FoodItemId>" + max + "</FoodItemId></FoodItemAdded>";
		/*
		 * ClassLoader classLoader = new
		 * FoodItemService().getClass().getClassLoader(); File file = new
		 * File(classLoader.getResource("FoodItemData.xml").getFile());
		 * 
		 * DocumentBuilderFactory dbFactory = DocumentBuilderFactory
		 * .newInstance(); DocumentBuilder dBuilder =
		 * dbFactory.newDocumentBuilder(); Document doc = dBuilder.parse(file);
		 * Element nList = doc.getDocumentElement();
		 * System.out.println("-----------------------");
		 *  Element newBook =
		 * doc.createElement("FoodItem"); Attr att =
		 * doc.createAttribute("country");
		 * att.setValue(AddList.get(0).getCountry());
		 * newBook.setAttributeNode(att);
		 * 
		 * Element bookId = doc.createElement("id");
		 * bookId.appendChild(doc.createTextNode(max.toString()));
		 * newBook.appendChild(bookId);
		 * 
		 * Element bookname = doc.createElement("name");
		 * bookname.appendChild(doc.createTextNode((AddList.get(0).getName())));
		 * newBook.appendChild(bookname);
		 * 
		 * Element bookAuthor = doc.createElement("description");
		 * bookAuthor.appendChild(doc.createTextNode((AddList.get(0)
		 * .getDescription()))); newBook.appendChild(bookAuthor);
		 * 
		 * Element bookYear = doc.createElement("category");
		 * bookYear.appendChild(doc.createTextNode((AddList.get(0)
		 * .getCategory()))); newBook.appendChild(bookYear); Element bookAvail =
		 * doc.createElement("price"); newBook.appendChild(bookAvail);
		 * 
		 * nList.appendChild(newBook);
		 * 
		 * Transformer transformer = TransformerFactory.newInstance()
		 * .newTransformer(); transformer.setOutputProperty(OutputKeys.INDENT,
		 * "yes");
		 * 
		 * // initialize StreamResult with File object to save to file
		 * StreamResult result = new StreamResult(new File("FoodItemData.xml"));
		 * DOMSource source = new DOMSource(doc); transformer.transform(source,
		 * result); System.out.println("DONE");
		 */
		return responseContent;
	}

}
