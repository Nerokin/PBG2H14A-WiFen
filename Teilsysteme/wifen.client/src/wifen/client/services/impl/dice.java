package wifen.client.services.impl;

import java.util.Random;

public class dice {
	
	//Erfolgstest:
	//Formel: XwYeZ+/-M X:Anzahl der WÃ¼rfel Y:ANzhal Augen Z:Schwelle 
    public int[] dice_Test(int x,int y,int z, int m){
        int out = 0;
        int[] dice = new int[x+1];
        Random r = new Random();
        for(int i = 0;i<=x-1;i++){
            dice[i] = r.nextInt(y)+1;
            if(dice[i]+m>z){
                out++;
            }
        }
        dice[x]=out;
        return dice;
    }
    
    //Formel: XwY+/-M
    // x: Anzahl der Würfel, y: Anzahl der Seiten, m: Modifikator
    public int[] dice_throw(int x,int y,int m){
    	int dice[] = new int[x+1];
    	Random ran = new Random();
    	for(int i = 1; i <= x; i++){
    		dice[i] = ran.nextInt(y)+1;
    		dice[0] += dice[i];
    	}
    	dice[0] += m;
    	return dice;
    }
    
    //"EreignisFenster.log(output);" , wenn nich satisch, objekt ansprechen
    public void output(int[] input){
    	String output = new String();
    	output = "Ergebniss: "+input[0]+" (";
    	for(int i = 1; i < input.length; i++){
    		output += " "+i+". Würfel: "+input[i];
    		if(i != input.length-1){
    			output+= ";";
    		}
    		else{
    			output += " )";
    		}
    	}
    	EreignisFenster.log(output);
    }
}
