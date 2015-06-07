package com.desire.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.desire.assist.Constant;
import com.desire.xml.MetaRead;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.tdb.*;

public class StoreManager {
	public static void main(String [] args){
		StoreManager m = new StoreManager();
		m.create();
		m.read();
	}
	Dataset dataset;
	public void create(){
		MetaRead metaRead = new MetaRead();
		try {
			//read metadata from xml file datalake
			Document doc = metaRead.createDocument();
			NodeList datalake = doc.getElementsByTagName("datalake");
			String filename = null;
			if(datalake != null){
				filename = datalake.item(0).getTextContent();
			}
			//create TDb dataset

			dataset =  TDBFactory.createDataset(filename);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TDb dataset should be closed after finishing
	public void close(){
		dataset.close();
	}
	//store triples to TDB dataset
	public void store(ArrayList<String> subjects, String predicate, String object){
		dataset.begin(ReadWrite.WRITE);
		Model model = dataset.getDefaultModel();

		for(String subject : subjects){
			model.add(model.createResource(subject), model.createProperty(predicate), object);
		}

		dataset.commit();
	}
	public void store(ArrayList<Triple> triples){
		dataset.begin(ReadWrite.WRITE);
		Model model = dataset.getDefaultModel();
		
		Iterator it = triples.iterator();
		while(it.hasNext()){
			Triple triple = (Triple)it.next();
			model.add(model.createResource(triple.getSubject()), model.createProperty(triple.getPredicate()), triple.getObject());
		}
		
		dataset.commit();
	}
	
	public Object [] read(String subject, String property, String object){
		if(subject == null && object == null){
			dataset.begin(ReadWrite.READ);
			Model model = dataset.getDefaultModel();
			NodeIterator objects = model.listObjectsOfProperty(model.createProperty(property));
			dataset.end();
			return objects.toList().toArray();
			
		}else if(subject == null){
			dataset.begin(ReadWrite.READ);
			Model model = dataset.getDefaultModel();
			ResIterator subjects = model.listSubjectsWithProperty(model.createProperty(property), object);
			dataset.end();
			return subjects.toList().toArray();
		}else if(object == null){
			dataset.begin(ReadWrite.READ);
			Model model = dataset.getDefaultModel();
			NodeIterator objects = model.listObjectsOfProperty(model.createResource(subject), model.createProperty(property));
			dataset.end();

			return objects.toList().toArray();
		}
		
		return null;
	}
	
	
	public void read(){
		System.out.println("begin read");
		dataset.begin(ReadWrite.READ);
		Model model = dataset.getDefaultModel();
//		System.out.println(model.createProperty(Constant.type));

		System.out.println(model.listSubjectsWithProperty(model.createProperty(Constant.type), "http://dbpedia.org/ontology/Museum").toList().size());
//		System.out.println(model.listObjectsOfProperty(model.createProperty(Constant.type)).toList());
		dataset.end();
	}
}
