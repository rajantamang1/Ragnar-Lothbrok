package com.util;
import java.sql.ClientInfoStatus;
import java.util.Random;
public class CombatEngine {

    //generate winner based on greater random number
    public int random(int a) { // a being greater than 50
        System.out.println("from random:" + a);
        Random rand = new Random();
        return rand.nextInt(a);
    }



    public int combatPower(int soldierCount, int weaponCount) {
        int result = random(soldierCount) + random(weaponCount);
        return result;
    }
}