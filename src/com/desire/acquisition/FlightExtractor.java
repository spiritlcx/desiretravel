package com.desire.acquisition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.jena.atlas.json.JsonValue;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.desire.assist.JsonModel;
import com.desire.xml.MetaRead;

public class FlightExtractor extends ApiExtractor{
	ArrayList<HashMap<String, String>> flightsources = new ArrayList<HashMap<String, String>>();
	public static void main(String [] args){
		FlightExtractor m = new FlightExtractor();
		m.exract("BCN", "Lon", "5/17/2015", "5/18/2015");
	}
	
	public FlightExtractor(){
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
							for(int k = 1; k < childinfo.getLength(); k++){
								Node info = childinfo.item(k);
								switch(k){
									case 1:
										depature = info.getTextContent();
										break;
									case 2:
										arrival = info.getTextContent();
										break;
									case 3:
										outbound_date = info.getTextContent();
										break;
									case 4:
										inbound_date = info.getTextContent();
										break;
								}								
							}
						}
					}
					flightsource.put("depature", depature);
					flightsource.put("arrival", arrival);
					flightsource.put("outbound_date", outbound_date);
					flightsource.put("inbound_date", inbound_date);
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

			String jsonRequest = "{'"+flightsource.get("depature")+"':'" + depature +
					"', '"+flightsource.get("arrival")+"':'" + arrival + "' , "
					+ "'"+flightsource.get("outbound_date")+"':'"+outbound_date
					+"' , '"+flightsource.get("inbound_date")+"': '"+inbound_date+"'}";

			JSONObject jsonObject = new JSONObject(jsonRequest);
			
			sendHttpRequest(flightsource.get("url"), jsonRequest);
		}
	}
}