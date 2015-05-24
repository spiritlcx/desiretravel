package com.desire.acquisition;

import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.desire.assist.Constant;
import com.desire.store.StoreManager;
import com.desire.store.Triple;
import com.desire.xml.MetaRead;

public class DbpediaExtractor extends SparqlExtractor {
	public static void main(String [] args){
		DbpediaExtractor db = new DbpediaExtractor();
		db.extract();
	}
	
	StoreManager storeManager = new StoreManager();
	
	public DbpediaExtractor(){
		storeManager.create();
	}
	
	public void extract(){
		try{
			MetaRead metaRead = new MetaRead();
			Document doc = metaRead.createDocument();
			
			NodeList endpointnode = doc.getElementsByTagName("endpoint");

			String endpoint = "";
			if(endpointnode != null){
				endpoint = endpointnode.item(0).getTextContent();
			}else{
				return;
			}			

			NodeList chars = doc.getElementsByTagName("characteristic");

			ArrayList<String> results = null;
			Node charnode = chars.item(0);

			if(charnode.hasChildNodes()){
				NodeList childrenNodes = charnode.getChildNodes();
				for(int count= 0; count < childrenNodes.getLength(); count++){
					Node childnode = childrenNodes.item(count);
					if(childnode.getNodeName().equals("uri")){
						String predicate = "rdf:type";
						String object = childnode.getTextContent();
						String rdfgraph = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
						"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
						"select ?instances where "+
						"{?instances " + predicate + " " + object + " }";
						
						results = sparqlQuery(endpoint, rdfgraph);
						ArrayList<String> subjects = new ArrayList<String>();
						
						if(subjects == null)
							continue;

						for(String result : results){
							subjects.add(result);
						}
						storeManager.store(subjects, predicate, object);

					}else if(childnode.getNodeName().equals("property")){
						if(childnode.hasChildNodes()){
							NodeList properties = childnode.getChildNodes();
							ArrayList<String> predicates = new ArrayList<String>();
							for(int i = 0; i < properties.getLength(); i++){
								predicates.add(properties.item(i).getTextContent());
							}
							
							ArrayList<Triple> triples = new ArrayList<Triple>();
							
							for(String subject : results){
								for(String predicate : predicates){
									String rdfgraph = "select ?instances where {<"+
											subject + "> <" + predicate + "> " + " ?instances}";
									
									ArrayList<String> objects = sparqlQuery(endpoint, rdfgraph);
									if(objects == null)
										continue;
									for(String object: objects)
										triples.add(new Triple(subject, predicate, object));
								}
							}
							storeManager.store(triples);
						}
					}
				}
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}