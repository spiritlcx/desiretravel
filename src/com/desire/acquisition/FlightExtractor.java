package com.desire.acquisition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.json.JsonObject;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.desire.store.StoreManager;
import com.desire.store.Triple;
import com.desire.transformer.DataTransformer;
import com.desire.transformer.TransformerFactory;
import com.desire.xml.MetaRead;


public class FlightExtractor extends ApiExtractor{
	ArrayList<HashMap<String, String>> flightsources = new ArrayList<HashMap<String, String>>();
	StoreManager storeManager = new StoreManager();

	public static void main(String [] args){
		
		FlightExtractor m = new FlightExtractor();
		m.exract("BCN", "Lon", "5/17/2015", "5/18/2015");
	}
	
	public FlightExtractor(){
		storeManager.create();
		try{
			MetaRead metaRead = new MetaRead();
			Document doc = metaRead.createDocument();
			NodeList companies = doc.getElementsByTagName("company");
			
			for(int i = 0; i < companies.getLength(); i++){
				JsonObject model = null;
				HashMap<String, String> flightsource = new HashMap<String, String>();
				String depature = "";
				String arrival = "";
				String outbound_date = "";
				String inbound_date = "";

				Node company = companies.item(i);
				if(company.hasChildNodes()){
					NodeList nodes = company.getChildNodes();
					for(int j = 0; j < nodes.getLength(); j++){
						Node node = nodes.item(j);
						if(node.getNodeName().equals("request")){
							NodeList childinfo = node.getChildNodes();
							flightsource.put("url", childinfo.item(0).getTextContent());
							flightsource.put("depature", childinfo.item(1).getTextContent());
							flightsource.put("arrival", childinfo.item(2).getTextContent());
							flightsource.put("outbound_date", childinfo.item(3).getTextContent());
							flightsource.put("inbound_date", childinfo.item(4).getTextContent());
						}else if(node.getNodeName().equals("response")){
							NodeList childinfo = node.getChildNodes();
							flightsource.put("format", childinfo.item(0).getTextContent());
							flightsource.put("flightcode", childinfo.item(1).getTextContent());
							flightsource.put("arrivaltime", childinfo.item(2).getTextContent());
							flightsource.put("departuretime", childinfo.item(3).getTextContent());
							flightsource.put("price", childinfo.item(4).getTextContent());
						}
					}
				}
				flightsources.add(flightsource);
			}
						
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void exract(String depature, String arrival, String outbound_date, String inbound_date){
		Iterator it = flightsources.iterator();
		while(it.hasNext()){
			HashMap<String, String> flightsource = (HashMap<String, String>)it.next();

			JSONObject jsonRequest = new JSONObject();
			jsonRequest.put(flightsource.get("depature"),depature);
			jsonRequest.put(flightsource.get("arrival"),arrival);
			jsonRequest.put(flightsource.get("outbound_date"),outbound_date);
			jsonRequest.put(flightsource.get("inbound_date"),inbound_date);
			
			String response = sendHttpRequest(flightsource.get("url"), jsonRequest);
			TransformerFactory transformerFactory = new TransformerFactory();
			DataTransformer transformer = transformerFactory.createTransformer(response, flightsource);
			if(transformer == null){
				System.out.println("the format is not supported");
			}else{
				storeManager.store(transformer.transform());
//				ArrayList<Triple> triples = transformer.transform();
				
//				System.out.println(triples.size());
			}

		}
	}
}