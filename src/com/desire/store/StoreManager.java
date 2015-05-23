package com.desire.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.desire.xml.MetaRead;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;

public class StoreManager {
	public static void main(String [] args){
		StoreManager m = new StoreManager();
		m.create();
		m.read();
//		m.store("", "", "");
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
	public void store(String subject, String predicate, String object){
		dataset.begin(ReadWrite.WRITE);
		Model model = dataset.getDefaultModel();
		model.add(model.createResource(subject), model.createProperty(predicate), object);
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
	public void read(){
		System.out.println("begin read");
		dataset.begin(ReadWrite.READ);
		Model model = dataset.getDefaultModel();
		System.out.println(model.listSubjects().toList());
		dataset.end();
	}
}
