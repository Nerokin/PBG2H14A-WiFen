package wifen.client.services.impl;

import java.util.Random;

public class dice {
	
	//Erfolgstest:
	//Formel: XwYeZ+/-M X:Anzahl der Würfel Y:ANzhal Augen Z:Schwelle 
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
}
