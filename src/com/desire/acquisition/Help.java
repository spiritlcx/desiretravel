package com.desire.acquisition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.desire.assist.Constant;
import com.desire.store.StoreManager;
import com.desire.store.Triple;

public class Help {
	public static void main(String [] args){
		StoreManager storeManager = new StoreManager();
		storeManager.create();
		ArrayList<Triple> triples = new ArrayList<Triple>();


		Object [] results = storeManager.read("http://www.spiritlcx.com/ontology/Costa Rica", "http://www.spiritlcx.com/continent", null);
		System.out.println(results[0]);
		System.out.println(results.length);
//		Object[] results = storeManager.read(null, "http://dbpedia.org/ontology/city", null);
//		int count = 0;
//		
//		for(Object subject : results){
//			String rdfgraph = "select ?instances where {<"+
//					subject + "> <" + "http://dbpedia.org/ontology/country" + "> " + " ?instances}";
//	
//			SparqlExtractor sparqlQuery = new SparqlExtractor();
//			
//			ArrayList<String> objects = sparqlQuery.sparqlQuery("http://dbpedia.org/sparql", rdfgraph);
//			if(objects == null)
//				continue;
//			for(Object object: objects){
//				count++;
//				triples.add(new Triple(subject.toString(), "http://dbpedia.org/ontology/country", object.toString()));
//			}
//		}
//		System.out.println(count);

//		Object[] results = storeManager.read(null, "http://dbpedia.org/ontology/country", null);
//
//		int count=0;
//		File file = new File("resources/Countries-Continents-csv.csv");
//	    try {
//	    	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//		    String line = "";
//	    	while((line = reader.readLine()) != null) {
//		    	String [] continentCountry = line.split(",");
//		    	String country = continentCountry[1].replace(" ", "_");
//		    	for(Object result : results){
//		    		if(result.toString().contains(country)){
//		    			triples.add(new Triple(result.toString(), "http://www.w3.org/2002/07/owl#sameAs" ,"http://www.spiritlcx.com/ontology/"+continentCountry[1]));
//		    			triples.add(new Triple("http://www.spiritlcx.com/ontology/"+continentCountry[1],"http://www.spiritlcx.com/continent","http://www.spiritlcx.com/ontology/"+continentCountry[0]));
//		    			break;
//		    		}
//		    	}
//		    }
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		storeManager.store(triples);
		
		storeManager.close();

		
	}
}



//StoreManager storeManager = new StoreManager();
//storeManager.create();
//
//Object[] results = storeManager.read(null, Constant.type, "http://dbpedia.org/ontology/Airport");
//
//ArrayList<Triple> triples = new ArrayList<Triple>();
//
//SparqlExtractor sparqlQuery = new SparqlExtractor();
//
//for(Object subject : results){
//	String rdfgraph = "select ?instances where {<"+
//			subject + "> <" + "http://dbpedia.org/ontology/city" + "> " + " ?instances}";
//			
//	ArrayList<String> objects = sparqlQuery.sparqlQuery("http://dbpedia.org/sparql", rdfgraph);
//	if(objects == null)
//		continue;
//	for(String object: objects){
//		triples.add(new Triple(subject.toString(), "http://dbpedia.org/ontology/city", object));
//		System.out.println(object);
//	}
//}
////storeManager.store(triples);
//storeManager.close();
