package wifen.commons.services.impl;


import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import wifen.commons.impl.PlayerImpl;
import wifen.client.application.ClientApplication;
import wifen.client.resources.Marker;
import wifen.client.services.ClientChatService;
import wifen.client.ui.controllers.ChatController;
import wifen.client.ui.controllers.EreignisFenster;
import wifen.client.ui.controllers.Spielfeld;

public class SaveGameStateToXML {

	int playerid = 0;
	int markerid = 0;
	
	public void saveToXML(String xml, List<PlayerImpl> players, Spielfeld spielfeld, List<Marker> markers, EreignisFenster ereignis) {
			
		// benötigt eine Liste der Spieler
		// benötigt eine Liste aller Marker auf dem Spielfeld
		// ChatHistory 
		// LogHistory
		
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			//Root
			Element rootElement = doc.createElement("Game");
			doc.appendChild(rootElement);
			
			Element player = doc.createElement("Players");
				rootElement.appendChild(player);
			
			// Nicht nötig aber geschrieben falls gebraucht
				/*
			for (Player p : players) {
				playerid++;
				//Player
				Element spieler = doc.createElement("Spieler");
				rootElement.appendChild(player);
				
				Attr attr = doc.createAttribute("SpielerID");
				attr.setValue("" + playerid);
				spieler.setAttributeNode(attr);
				
				// Name des Spielers
				Element playerName = doc.createElement("Spielername");
				playerName.appendChild(doc.createTextNode(p.getName()));
				player.appendChild(playerName);
				
				
				
				// Rolle des Spielers
				Element playerRole = doc.createElement("Spielerrolle");
				playerRole.appendChild(doc.createTextNode(p.getRolle().toString()));
				player.appendChild(playerRole);
			
			}
			*/
			
			// Spielfeld
			Element field = doc.createElement("Spielfeld");
			rootElement.appendChild(field);
			// JAXB?
			
			Element fieldType = doc.createElement("Raster");
			fieldType.appendChild(doc.createTextNode("" + spielfeld.getTyp()));
			field.appendChild(fieldType);
			
			/**initialize a field with a rectangle grid
			 * sizeFieldX => Stack width
			 * sizeFieldY => Stack height
			 * sizeSceneX => Scene width
			 * sizeSceneY => Scene height
			 * tilesPerRow => rectangle per row
			 * tilesPerCol => rectangle per column*/
			
			Element sizeFieldX = doc.createElement("Spielfeldbreite");
			sizeFieldX.appendChild(doc.createTextNode("" + spielfeld.getSizeFieldX()));
			field.appendChild(sizeFieldX);
			
			Element sizeFieldY = doc.createElement("Spielfeldhöhe");
			sizeFieldY.appendChild(doc.createTextNode("" + spielfeld.getSizeFieldY()));
			field.appendChild(sizeFieldY);
			
			Element sizeSceneX = doc.createElement("Fensterbreite");
			sizeSceneX.appendChild(doc.createTextNode("" + spielfeld.getSizeSceneX()));
			field.appendChild(sizeSceneX);
			
			Element sizeSceneY = doc.createElement("Fensterhöhe");
			sizeSceneY.appendChild(doc.createTextNode("" + spielfeld.getSizeSceneY()));
			field.appendChild(sizeSceneY);
			
			Element tilesPerRow = doc.createElement("Kacheln_pro_Reihe");
			tilesPerRow.appendChild(doc.createTextNode("" + spielfeld.getTilesPerRow()));
			field.appendChild(tilesPerRow);
			
			Element tilesPerCol = doc.createElement("Kacheln_pro_Spalte");
			tilesPerCol.appendChild(doc.createTextNode("" + spielfeld.getTilesPerCol()));
			field.appendChild(tilesPerCol);
			
			
			// Hier wird die Liste der Marker gespeichert
			
			for (Marker m : markers) {
				markerid++;
				Element marker = doc.createElement("Marker");
				field.appendChild(marker);
				
				Attr markerAttr = doc.createAttribute("MarkerID");
				markerAttr.setValue("" + markerid);
				marker.setAttributeNode(markerAttr);
				
				Element markerX = doc.createElement("MarkerX");
				marker.appendChild(doc.createTextNode("" + m.getTranslateX()));
				marker.appendChild(fieldType);
				
				Element markerY = doc.createElement("MarkerY");
				marker.appendChild(doc.createTextNode("" + m.getTranslateY()));
				marker.appendChild(fieldType);
				
				Element markerType = doc.createElement("MarkerTyp");
				marker.appendChild(doc.createTextNode("" + m.getType().getName()));
				marker.appendChild(fieldType);
				
				// Alle weiteren wichtigen Attribute des Markers
				
			}
			
			// Chat und Ereignislog
			Element logs = doc.createElement("Logs");
			rootElement.appendChild(logs);
			
			Element chatLog = doc.createElement("Chat");
			logs.appendChild(chatLog);
			
			int linecountC = 0;
			
			for (String s : ClientApplication.instance().getServiceRegistry().getServiceProviderByClass(ClientChatService.class).getChatHistory()){

				Element lines = doc.createElement("Chat_Zeilen");
				lines.appendChild(doc.createTextNode(s));
				chatLog.appendChild(lines);
				
				Attr lineNumber = doc.createAttribute("ZeilennummerC");
				lineNumber.setValue("" + linecountC);
				lines.setAttributeNode(lineNumber);
				linecountC++;
				
			}
			
			
			// EreignisLog muss nochmal überarbeitet werden
			/*
			
			Element ereignisLog = doc.createElement("Chat");
			logs.appendChild(ereignisLog);
			
			int linecountE = 0;
			
			for (String s : ereignis.getLog()){

				Element lines = doc.createElement("Ereignis_Zeilen");
				lines.appendChild(doc.createTextNode(s));
				ereignisLog.appendChild(lines);
				
				Attr lineNumber = doc.createAttribute("ZeilennummerE");
				lineNumber.setValue("" + linecountE);
				lines.setAttributeNode(lineNumber);
				linecountE++;
				
			}
			*/
			
			
			
			
			//http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files
			
			// Write
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = transformerFactory.newTransformer();
				
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\file.xml"));
				
				transformer.transform(source, result);
				
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block			
			
			e.printStackTrace();
		}
		
	}
}
