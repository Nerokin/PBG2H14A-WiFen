package wifen.client.services.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import wifen.client.ui.controllers.EreignisFenster;

/**
 * Bereitstellung der Würfelfunktionen: Normaler Würfelwurf (mit Modifikator), Schwellentest (mit Modifikator)
 * 
 * @author David Kühlmann, Johnny Gunko
 *
 */
public class dice {
	// -----------------------
	// David Kühlmann -- Start
	// -----------------------
	// Überprüfung des Strings der Texteigabe des Würfelfeldes
	/**
	 *  Überprüfung des Strings der Texteingabe des Würfelfensters
	 * 
	 * @param in Würfelausdruck der Texteingabe des Würfelfensters
	 * @return
	 */
	public static String checkInput(String in){
        //Formel: XwY+M
        if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*$"))
        { 
            int index_x = in.indexOf('w');
            int index_y = in.indexOf('+',index_x);
            String in_x = in.substring(0,index_x);
            String in_y = in.substring(index_x+1, index_y);
            String in_m = in.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int m = Integer.parseInt(in_m);
            return outputThrow(dice_Throw(x,y,m));    
        }
        //Formel: XwY-M
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*$"))
        { 
            int index_x = in.indexOf('w');
            int index_y = in.indexOf('-',index_x);
            String in_x = in.substring(0,index_x);
            String in_y = in.substring(index_x+1, index_y);
            String in_m = in.substring(index_y);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int m = Integer.parseInt(in_m);
             return outputThrow(dice_Throw(x,y,-m));  
        }
        //Formel: XwY
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*$"))
        {
            String[] in_split = in.split("w");
            int x = Integer.parseInt(in_split[0]);
            int y = Integer.parseInt(in_split[1]);
            return outputThrow(dice_Throw(x,y,0));  
        }
        //Formel: XwYeZ+M
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\+[1-9][0-9]*$"))
        {
            int index_x = in.indexOf('w');
            int index_y = in.indexOf('e', index_x);
            int index_m = in.indexOf('+', index_y);
            String in_x = in.substring(0, index_x);
            String in_y = in.substring(index_x+1, index_y);
            String in_z = in.substring(index_y+1, index_m);
            String in_m = in.substring(index_m+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            int m = Integer.parseInt(in_m);
            return outputTest(dice_Test(x,y,z,m));    
        }
        //Formel: XwYeZ-M
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\-[1-9][0-9]*$"))
        { 
            int index_x = in.indexOf('w');
            int index_y = in.indexOf('e', index_x);
            int index_m = in.indexOf('-', index_y);
            String in_x = in.substring(0, index_x);
            String in_y = in.substring(index_x+1, index_y);
            String in_z = in.substring(index_y+1, index_m);
            String in_m = in.substring(index_m);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            int m = Integer.parseInt(in_m);
            return outputTest(dice_Test(x,y,z,-m));    
        }
        //Formel: XwYeZ
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*$"))
        {
            int index_x = in.indexOf('w');
            int index_y = in.indexOf('e', index_x);
            String in_x = in.substring(0, index_x);
            String in_y = in.substring(index_x+1, index_y);
            String in_z = in.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,0)); 
        }
        //Formel: XwYeZ+(XwY+M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('+');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('+',index_m_x);
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //Auswertung des ersten Ausdrucks um End Ergebniss zu bestimmen
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e');
            String in_x = erster.substring(0,index_x);
            String in_y = erster.substring(index_x+1,index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z  =Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,m));
            //System.out.println(zweiter);
        }
        //Formel: XwYeZ+(XwY-M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('+');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('-',index_m_x);
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //Auswertung 1. AUsdrucksteil, Endberechnung
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e', index_x);
            String in_x = erster.substring(0, index_x);
            String in_y = erster.substring(index_x+1, index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,m)); 
        }
        //Formel: XwYeZ-(XwY+M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('-');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('+',index_m_x);
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //Auswertung 1. AUsdrucksteil, Endberechnung
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e', index_x);
            String in_x = erster.substring(0, index_x);
            String in_y = erster.substring(index_x+1, index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,-m)); 
        }
        //Formel: XwYeZ-(XwY-M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('-');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('-',index_m_x);
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //Auswertung 1. AUsdrucksteil, Endberechnung
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e', index_x);
            String in_x = erster.substring(0, index_x);
            String in_y = erster.substring(index_x+1, index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,-m)); 
        }
        //Formel: XwYeZ+(XwY)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('+');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m = dice_Throw(m_x,m_y,0)[0];
            //Auswertung 1. AUsdrucksteil, Endberechnung
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e', index_x);
            String in_x = erster.substring(0, index_x);
            String in_y = erster.substring(index_x+1, index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,m)); 
        }
        //Formel: XwYeZ-(XwY)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*e[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('-');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //Auswertung des zweiten Ausdrucks zur Malusberechnung
            int index_m_x = zweiter.indexOf('w');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m = dice_Throw(m_x,m_y,0)[0];
            //Auswertung 1. AUsdrucksteil, Endberechnung
            int index_x = erster.indexOf('w');
            int index_y = erster.indexOf('e', index_x);
            String in_x = erster.substring(0, index_x);
            String in_y = erster.substring(index_x+1, index_y);
            String in_z = erster.substring(index_y+1);
            int x = Integer.parseInt(in_x);
            int y = Integer.parseInt(in_y);
            int z = Integer.parseInt(in_z);
            return outputTest(dice_Test(x,y,z,-m)); 
        }
        //Formel: XwY+(XwY+M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('+');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //2. Ausdruck
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('+');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //1. Ausdruck
            String[] in_split = erster.split("w");
            int x = Integer.parseInt(in_split[0]);
            int y = Integer.parseInt(in_split[1]);
            return outputThrow(dice_Throw(x,y,m));  
        }
        //Formel: XwY+(XwY-M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('+');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //2. Ausdruck
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('-');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //1. Ausdruck
            String[] in_split = erster.split("w");
            int x = Integer.parseInt(in_split[0]);
            int y = Integer.parseInt(in_split[1]);
            return outputThrow(dice_Throw(x,y,m));  
        }
        //Formel: XwY-(XwY+M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('-');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //2. Ausdruck
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('+');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,m_m)[0];
            //1. Ausdruck
            String[] in_split = erster.split("w");
            int x = Integer.parseInt(in_split[0]);
            int y = Integer.parseInt(in_split[1]);
            return outputThrow(dice_Throw(x,y,-m));  
        }
        //Formel: XwY-(XwY-M)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*$"))
        {
            int index_split = in.indexOf('-');
            String erster = in.substring(0,index_split);
            String zweiter = in.substring(index_split+1);
            //2. Ausdruck
            int index_m_x = zweiter.indexOf('w');
            int index_m_y = zweiter.indexOf('-');
            String in_m_x = zweiter.substring(0,index_m_x);
            String in_m_y = zweiter.substring(index_m_x+1, index_m_y);
            String in_m_m = zweiter.substring(index_m_y+1);
            int m_x = Integer.parseInt(in_m_x);
            int m_y = Integer.parseInt(in_m_y);
            int m_m = Integer.parseInt(in_m_m);
            int m = dice_Throw(m_x,m_y,-m_m)[0];
            //1. Ausdruck
            String[] in_split = erster.split("w");
            int x = Integer.parseInt(in_split[0]);
            int y = Integer.parseInt(in_split[1]);
            return outputThrow(dice_Throw(x,y,-m));  
        }
        //Formel: XwY+(XwY)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\+[1-9][0-9]*w[1-9][0-9]*$"))
        {
            String[] in_split = in.split("\\+");
            String[] erster = in_split[0].split("w");
            String[] zweiter = in_split[1].split("w");
            //2. Ausdruck
            int m_x = Integer.parseInt(zweiter[0]);
            int m_y = Integer.parseInt(zweiter[1]);
            int m = dice_Throw(m_x,m_y,0)[0];
            int x = Integer.parseInt(erster[0]);
            int y = Integer.parseInt(erster[1]);
            return outputThrow(dice_Throw(x,y,m));  
        }
        //Formel: XwY-(XwY)
        else if(in.matches("^[1-9][0-9]*w[1-9][0-9]*\\-[1-9][0-9]*w[1-9][0-9]*$"))
        {
            String[] in_split = in.split("-");
            String[] erster = in_split[0].split("w");
            String[] zweiter = in_split[1].split("w");
            //2. Ausdruck
            int m_x = Integer.parseInt(zweiter[0]);
            int m_y = Integer.parseInt(zweiter[1]);
            int m = dice_Throw(m_x,m_y,0)[0];
            //1. Ausdruck
            int x = Integer.parseInt(erster[0]);
            int y = Integer.parseInt(erster[1]);
            return outputThrow(dice_Throw(x,y,-m));  
        }
        else{
            return null;
        }     
}

	// Formel: XwYeZ+/-M X:Anzahl der Würfel Y:ANzhal Augen Z:Schwelle
	/**
	 * Methode um einen Schwellentest mit der Formlel XwYeZ+/-M durchzuführen
	 * 
	 * @param x Anzahl der zu Werfenden Würfel
	 * @param y Anzahl der Würfelaugen
	 * @param z Schwelle die erreicht werden oder überschritten werden muss
	 * @param m Zusätzlicher Modifikator für Positive oder Negative effekte
	 * @return gibt die Werte des Wurfes als int array aus
	 */
	public static int[] dice_Test(int x, int y, int z, int m) {
		int out = 0;
		int[] dice = new int[x + 1];
		Random r = new Random();
		for (int i = 0; i <= x - 1; i++) {
			dice[i] = r.nextInt(y) + 1;
			if (dice[i] + m >= z) {
				out++;
			}
		}
		dice[x] = out;
		return dice;
	}

	// -----------------------
	// David Kühlmann -- Ende
	// -----------------------
	
	// -----------------------
	// Johnny Gunko -- Start
	// -----------------------
	
	// Formel: XwY+/-M
	// x: Anzahl der W�rfel, y: Anzahl der Seiten, m: Modifikator
	/**
	 * Methode um einen Schwellentest mit der Formlel XwYeZ+/-M durchzuf�hren
	 * 
	 * @param x
	 * @param y
	 * @param m
	 * @return gibt die Werte des Wurfes als int array aus
	 */
	public static int[] dice_Throw(int x, int y, int m) {
		int dice[] = new int[x + 1];
		Random ran = new Random();
		for (int i = 0; i <= x - 1; i++) {
			dice[i] = ran.nextInt(y) + 1;
			dice[x] += dice[i];
		}
		dice[x] += m;
		return dice;
	}

	/**
	 * Wandelt die Werte eines eines normalen Wurfes  in einen String um
	 * 
	 * @param input Wurfwerte
	 * @return Gibt den Wurfausdruck als String zur�ck
	 */
	public static String outputThrow(int[] input){
    	String output = new String();
    	output = "Ergebniss: "+input[input.length-1]+" (";
    	for(int i = 0; i < input.length-1; i++){
    		output += " "+(i+1)+". Wuerfel: "+input[i];
    		if(i != input.length-2){
    			output+= ";";
    		}
    		else{
    			output += " )";
    		}
    	}
    	return output;
    }
    
	/**
	 * Wandelt die Werte eines Testwurfes in einen String um
	 * 
	 * @param input Wurfwerte
	 * @return Gibt den Wurfausdruck als String zur�ck
	 */
    public static String outputTest(int[] input){
    	String output = new String();
    	output = "Erfolgreiche Wuerfe: "+input[input.length-1]+" (";
    	for(int i = 0; i < input.length-1; i++){
    		output += " "+(i+1)+". Wuerfel: "+input[i];
    		if(i != input.length-2){
    			output+= ";";
    		}
    		else{
    			output += " )";
    		}
    	}
    	return output;
    }

	/**
	 * Spielt einen Sound ab, der das W�feln, akustisch wiedergeben soll
	 * 
	 * @throws IOException
	 */
    public static void playWuerfeln() throws IOException{
		String wuerfelSound = "./ressources/wuerfeln.WAV";
		InputStream in = new FileInputStream(wuerfelSound);
		AudioStream audio = new AudioStream(in);
		AudioPlayer.player.start(audio);
    }
    
 // -----------------------
 // Johnny Gunko -- Ende
 // -----------------------

}
