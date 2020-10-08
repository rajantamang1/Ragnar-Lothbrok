package com.util;
import java.util.Random;
public class CombatEngine {

    //generate winner based on greater random number
    public int random(int a){ // a being greater than 50
        Random rand = new Random();
        return rand.nextInt(a);
    }

    public String combat(int soldierCount1, int soldierCount2){
        String message= "";
        if (random(soldierCount1)>random(soldierCount2)){
            message="Winner is soldier 1";
        } else {
            message= "Winner is soldier 2";
        }
        return message;
    }

//    public static void main(String[] args) {
//        CombatEngine combatEngine = new CombatEngine();
//        combatEngine.combat(50,100);
//        combatEngine.combat(50,100);
//        combatEngine.combat(50,100);
//    }
}
