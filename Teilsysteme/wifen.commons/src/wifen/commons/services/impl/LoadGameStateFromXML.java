package wifen.commons.services.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wifen.client.ui.controllers.Spielfeld;
import wifen.commons.SpielerRolle;
import wifen.commons.impl.Player;

public class LoadGameStateFromXML {
	
	public void loadSaveGame(){
	
		try {
	
			File fXmlFile = new File("DateiPfad to be determinded");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
	
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList pList = doc.getElementsByTagName("Players");
				
			for (int temp = 0; temp < pList.getLength(); temp++) {
				Node nNode = pList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					// Hier sind die Spieler geladen, was soll damit nun passieren?
					Element pElement = (Element) nNode;
	
					Player player = new Player(pElement.getElementsByTagName("Spielername").item(0).getTextContent());
					if(pElement.getElementsByTagName("SpielerRolle").item(0).getTextContent().equals("ADMIN")){
						player.setRolle(SpielerRolle.ADMIN);
					}
					else if(pElement.getElementsByTagName("SpielerRolle").item(0).getTextContent().equals("MODERATOR")){
						player.setRolle(SpielerRolle.MODERATOR);
					}
					else if(pElement.getElementsByTagName("SpielerRolle").item(0).getTextContent().equals("PLAYER")){
						player.setRolle(SpielerRolle.PLAYER);
					}
					else if(pElement.getElementsByTagName("SpielerRolle").item(0).getTextContent().equals("SPECTATOR")){
						player.setRolle(SpielerRolle.SPECTATOR);
					}					
				}
			}
			
			NodeList fList = doc.getElementsByTagName("Spielfeld");
			
			for (int temp = 0; temp < fList.getLength(); temp++) {
				Node nNode = fList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					// Hier sind die Spieler geladen, was soll damit nun passieren?
					Element fElement = (Element) nNode;
	
					if(Integer.parseInt(fElement.getElementsByTagName("Raster").item(0).getTextContent()) == 0){
						Spielfeld spielfeld = new Spielfeld(
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldhöhe").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterhöhe").item(0).getTextContent())
								);
					}
					else if(Integer.parseInt(fElement.getElementsByTagName("Raster").item(0).getTextContent()) == 1){
						Spielfeld spielfeld = new Spielfeld(
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldhöhe").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterhöhe").item(0).getTextContent()),
								Integer.parseInt(fElement.getElementsByTagName("Kacheln_pro_Reihe").item(0).getTextContent()),
								Integer.parseInt(fElement.getElementsByTagName("Kacheln_pro_Spalte").item(0).getTextContent())
								);
					}
					else if(Integer.parseInt(fElement.getElementsByTagName("Raster").item(0).getTextContent()) == 2){
						Spielfeld spielfeld = new Spielfeld(
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Spielfeldhöhe").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterbreite").item(0).getTextContent()),
								Double.parseDouble(fElement.getElementsByTagName("Fensterhöhe").item(0).getTextContent()),
								Integer.parseInt(fElement.getElementsByTagName("Kacheln_pro_Spalte").item(0).getTextContent())
								);
					}						
				}
			}
			
			NodeList mList = doc.getElementsByTagName("Marker");
			
			for (int temp = 0; temp < mList.getLength(); temp++) {
				Node nNode = mList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					// Hier sind die Spieler geladen, was soll damit nun passieren?
					Element mElement = (Element) nNode;
	
					Marker marker = new Marker();
					
					// Alle MArker Sachen die es noch nicht gibt werden hier geladen
											
				}
			}
			
			NodeList cList = doc.getElementsByTagName("Chat_Zeilen");
			ObservableList<String> chatHistory = FXCollections.observableArrayList();
			for (int temp = 0; temp < cList.getLength(); temp++) {
				Node nNode = cList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					// Hier sind die Spieler geladen, was soll damit nun passieren?
					Element cElement = (Element) nNode;
					
					chatHistory.add(cElement.getElementsByTagName("Chat_Zeilen").item(temp).getTextContent());
											
				}
			}
			
			NodeList eList = doc.getElementsByTagName("Ereignis_Zeilen");
			ObservableList<String> ereignistHistory = FXCollections.observableArrayList();
			for (int temp = 0; temp < cList.getLength(); temp++) {
				Node nNode = cList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					// Hier sind die Spieler geladen, was soll damit nun passieren?
					Element eElement = (Element) nNode;
					
					ereignistHistory.add(eElement.getElementsByTagName("Ereignis_Zeilen").item(temp).getTextContent());
											
				}
			}
			
			
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}

