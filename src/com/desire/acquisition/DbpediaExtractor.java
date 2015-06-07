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
			
			NodeList dbpedia = doc.getElementsByTagName("dbpedia").item(0).getChildNodes();
			String endpoint = "";

			for(int i = 0; i < dbpedia.getLength(); i++){
			
				if(dbpedia.item(i).getNodeName().equals("endpoint")){
					endpoint = dbpedia.item(i).getTextContent();
					System.out.println(endpoint);
					continue;
				}

				else if(dbpedia.item(i).getNodeName().equals("information")){
					NodeList info = dbpedia.item(i).getChildNodes();
					for(int j = 0; j < info.getLength(); j++){
						
						Node charnode = info.item(j);
						
						if(charnode.hasChildNodes()){
							ArrayList<String> results = null;
							NodeList childrenNodes = charnode.getChildNodes();
							for(int count= 0; count < childrenNodes.getLength(); count++){

								Node childnode = childrenNodes.item(count);
								if(childnode.getNodeName().equals("uri")){
									String predicate = Constant.type;
									String object = childnode.getTextContent();
									String rdfgraph = "select ?instances where " + 
									"{?instances " + "<"+ predicate + "> <" + object + ">}";

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
										for(int k = 0; k < properties.getLength(); k++){
											predicates.add(properties.item(k).getTextContent());
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
					}
				}
			}
			storeManager.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}