package com.desire.transformer;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.desire.assist.Constant;
import com.desire.store.Triple;

public class XMLTransformer extends DataTransformer{

	public XMLTransformer(String response, HashMap<String, String> flightsource) {
		super(response, flightsource);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Triple> transform(){
		ArrayList<Triple> triples = new ArrayList<Triple>();
		
		DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
        try{
        	DocumentBuilder build = dFact.newDocumentBuilder();
        	Document doc = build.parse(new InputSource(new ByteArrayInputStream(response.getBytes("utf-8"))));
        	NodeList flights = doc.getElementsByTagName("flight");
        	for(int i = 0; i < flights.getLength(); i++){
        		Node flight = flights.item(i);
        		NodeList flightinfo = flight.getChildNodes();

        		String flightCode = "";
        		String departuretime = "";
        		String arrivaltime = "";
        		String price = "";
        		
        		for(int j = 0; j < flightinfo.getLength(); j++){
        			Node info = flightinfo.item(j);
        			
        			if(flightsource.get("flightCode")==info.getNodeName()){
        				flightCode = info.getTextContent();
        			}else if(flightsource.get("departuretime")==info.getNodeName()){
        				departuretime = info.getTextContent();
        			}else if(flightsource.get("arrivaltime")==info.getNodeName()){
        				arrivaltime = info.getTextContent();
        			}else if(flightsource.get("price")==info.getNodeName()){
        				price = info.getTextContent();
        			}        			
        		}
    			triples.add(new Triple(flightCode, Constant.type, Constant.flight));
    			triples.add(new Triple(flightCode, Constant.arrivaltime, arrivaltime));
    			triples.add(new Triple(flightCode, Constant.depaturetime, departuretime));
    			triples.add(new Triple(flightCode, Constant.price, price));

        	}
        }catch(Exception e){
        	e.printStackTrace();
        }
        
		return triples;
	}
}
