package com.desire.recommendation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.desire.acquisition.FlightExtractor;
import com.desire.assist.Constant;
import com.desire.entity.Characteristic;
import com.desire.entity.Flight;
import com.desire.entity.Place;
import com.desire.store.StoreManager;
import com.desire.xml.MetaRead;

public class TripleReader {
	StoreManager storeManager = new StoreManager();
	public TripleReader(){
		storeManager.create();
	}
	public static void main(String [] args){
		
//		TripleReader tripleReader = new TripleReader();
//		tripleReader.read("http://dbpedia.org/resource/Moscow", null, null);
	}
	
	public ArrayList<Flight> read(String user_location, String character, String continent, String inboundate, String outboundate, int days){
		ArrayList<Flight> flights = new ArrayList<Flight>();
		
		MetaRead metaRead = new MetaRead();
		Document doc = null;
		try {
			doc = metaRead.createDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		NodeList characteristics = doc.getElementsByTagName("characteristic");
		for(int i = 0; i < characteristics.getLength(); i++){
			NodeList characteristic = characteristics.item(i).getChildNodes();

			Object [] subjects = null;
			String entity = null;
			for(int j = 0; j < characteristic.getLength(); j++){
				if(characteristic.item(j).getNodeName().equals("name")){
					entity = characteristic.item(j).getTextContent();
					if(!entity.equals(character))
						break;
				}else if(characteristic.item(j).getNodeName().equals("uri")){
					subjects = storeManager.read(null, Constant.type, characteristic.item(j).getTextContent());
				}else if(characteristic.item(j).getNodeName().equals("property")){
					NodeList property = characteristic.item(j).getChildNodes();
					
					try {
						Class[] paramString = new Class[1];
						paramString[0] = String.class;
						
						Class[] param = new Class[2];	
						param[0] = Object[].class;
						param[1] = NodeList.class;
						
						Class cls = Class.forName("com.desire.entitymanager.EntityManager");
						Constructor constructor = cls.getConstructor(paramString);
						Object obj = constructor.newInstance(entity);
						
						Method method = cls.getDeclaredMethod("generateEntities", param);
						ArrayList<Characteristic> chars =  (ArrayList<Characteristic>) method.invoke(obj, subjects, property);
						
						HashMap<String, Double> citymark = new HashMap<String, Double>();
						HashMap<String, ArrayList<String>> citychar = new HashMap<String, ArrayList<String>>();
						
						HashMap<String, Integer> citycontinent = new HashMap<String, Integer>();
						
						for(Characteristic cha : chars){
							if(cha.getCity() == null){
								continue;
							}else{

								Object [] country = storeManager.read(cha.getCity(), "http://dbpedia.org/ontology/country", null);
								if(country.length != 0){
									Object [] desirecountry = storeManager.read(country[0].toString(), "http://www.w3.org/2002/07/owl#sameAs", null);
									if(desirecountry.length != 0){
										Object [] desirecontinent = storeManager.read(desirecountry[0].toString(), "http://www.spiritlcx.com/continent", null);
										
										if(desirecontinent.length != 0){
											if(!desirecontinent[0].toString().equals(continent)){
//												System.out.println("desirecontinent:"+desirecontinent[0].toString());
												continue;
											}else{
												citycontinent.put(cha.getCity(), 1);
											}
										}else{
											continue;
										}
									}
								}
								
								ArrayList<String> citycha= citychar.get(cha.getCity());
								if(citycha == null){
									ArrayList<String> newcitycha = new ArrayList<String>();
									newcitycha.add(cha.getUri());
									citychar.put(cha.getCity(), newcitycha);
								}else{
									citycha.add(cha.getUri());									
								}

								if(citymark.get(cha.getCity()) == null){
									citymark.put(cha.getCity(), cha.getMark());
								}else{
									citymark.put(cha.getCity(), cha.getMark()+citymark.get(cha.getCity()));
								}
							}
						}

						List<Place> places = new ArrayList<Place>();
						
						for(Entry<String, Double> m :citymark.entrySet()){
							Place place = new Place();
							if(citycontinent.get(m.getKey()) == null)
								continue;
							place.setName(m.getKey());
							place.setMark(m.getValue());
							places.add(place);
						}
						
						Collections.sort(places);

						int count = 0;
						
						//places
						for(Place place : places){
							if(count++ > 10)
								break;

							System.out.println("cityname:"+place.getName());
							for(String name : citychar.get(place.getName())){
								System.out.println(name);
							}
														
							ArrayList<Flight> flightsinfo = new ArrayList<Flight>();
								FlightExtractor m = new FlightExtractor();
								ArrayList<Flight> flightinfo = m.exract("London", "Barcelona", inboundate, outboundate);

								flights = flightinfo;
								
								for(Flight flight : flightinfo){
									flightsinfo.add(flight);
//									System.out.println(flight.getPrice());
								}							
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		storeManager.close();
		return flights;
	}
}