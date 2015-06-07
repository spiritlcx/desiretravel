package com.desire.entitymanager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NodeList;

import com.desire.acquisition.SparqlExtractor;
import com.desire.entity.Characteristic;
import com.desire.entity.Museum;
import com.desire.store.StoreManager;

public class EntityManager {
	private String entityName;
	private StoreManager storeManager = new StoreManager();
	public EntityManager(String entityName){
		this.entityName = entityName;
		storeManager.create();
	}
	public ArrayList<Characteristic> generateEntities(Object [] subjects, NodeList property){
		ArrayList<Characteristic> entities = new ArrayList<Characteristic>();

		Class[] param = new Class[1];
		param[0] = String.class;
		
		Class cls = null;
		Object obj = null;
		try {
			cls = Class.forName("com.desire.entity." + entityName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap<String, String> properties = new HashMap<String, String>();

		SparqlExtractor ex = new SparqlExtractor();

		int count = 0;
		
		for(int i = 0; i < subjects.length; i++){
			try{
				obj = cls.newInstance();
				Method method = cls.getMethod("setUri", param);
				method.invoke(obj, subjects[i].toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			

			
			String [] names = subjects[i].toString().split("/");
			String name = names[names.length-1];
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			try{
				Method method = cls.getMethod("setName", param);
				method.invoke(obj, name);
			}catch(Exception e){
				e.printStackTrace();
			}
			for(int j = 0; j < property.getLength(); j++){
				Object [] objects = storeManager.read(subjects[i].toString(), property.item(j).getTextContent(),null);

				if(objects.length == 0)
					continue;
				
				String pro = properties.get(property.item(j).getTextContent());
				if(pro == null){
					String [] pros = property.item(j).getTextContent().split("/");
					pro = pros[pros.length-1];
					pro = pro.substring(0, 1).toUpperCase() + pro.substring(1);
					properties.put(property.item(j).getTextContent(), pro);
				}
				
				try {
					Method method = cls.getMethod("set" + pro, param);
					method.invoke(obj, objects[0].toString());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			entities.add((Characteristic)obj);
		}
		return entities;
	}
}
