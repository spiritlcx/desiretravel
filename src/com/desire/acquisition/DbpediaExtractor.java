package com.desire.acquisition;

import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.desire.store.StoreManager;
import com.desire.xml.MetaRead;

public class DbpediaExtractor extends SparqlExtractor {
	public static void main(String [] args){
		DbpediaExtractor db = new DbpediaExtractor();
		db.extract();
	}
	
	StoreManager storeManager = new StoreManager();
	
	public void extract(){
		storeManager.create();
		try{
			MetaRead metaRead = new MetaRead();
			Document doc = metaRead.createDocument();
			NodeList endpointnode = doc.getElementsByTagName("endpoint");
			String endpoint = "";
			if(endpointnode != null)
				endpoint = endpointnode.item(0).getTextContent();
			
			NodeList chars = doc.getElementsByTagName("characteristic");
			int numchars = chars.getLength();
			for(int i = 0; i < numchars; i++){
				ArrayList<String> results = null;
				Node node = chars.item(i);
				if(node.hasChildNodes()){
					NodeList childrenNodes = node.getChildNodes();
					for(int count= 0; count < childrenNodes.getLength(); count++){
						Node childnode = childrenNodes.item(count);
						if(childnode.getNodeName().equals("uri")){
							String predicate ="rdf:type";
							String object = childnode.getTextContent();
							String rdfgraph = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
							"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
							"select ?instances where "+
							"{?instances " + predicate + " " + object + " }";

							results = sparqlQuery(endpoint, rdfgraph);
							Iterator it = results.iterator();
							while(it.hasNext()){
								String instance = (String)it.next();
								storeManager.store(instance, predicate, object);
							}
							storeManager.close();
							storeManager.create();
							storeManager.read();

						}else if(childnode.getNodeName().equals("property")){
							if(childnode.hasChildNodes()){
								NodeList properties = childnode.getChildNodes();
								Iterator it = results.iterator();

								while(it.hasNext()){
									String instance = (String)it.next();
									
									for(int propertycount = 0; propertycount < properties.getLength(); propertycount++){
										Node property = properties.item(propertycount);
										String subject = instance;
										String predicate = property.getTextContent();

										String rdfgraph = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
												"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
												"select ?instances where {<"+
												subject + "> <" + predicate + "> " + " ?instances}";

										ArrayList<String> result = sparqlQuery(endpoint, rdfgraph);
										Iterator it_ = result.iterator();
										while(it_.hasNext()){
//											System.out.println(subject +"  " + predicate + "  " + (String)it_.next());
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}