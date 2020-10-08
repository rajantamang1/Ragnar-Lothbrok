package com.game;

import com.util.CombatEngine;

public class Start {

    public static void newGame(){
        Country country = new Country();
        CombatEngine combatEngine = new CombatEngine();
        String curCountry = "";
        while (true){

            curCountry = Prompter.promptUser(country);
            country.setNameOfCountry(curCountry);

            //message=combatEngine.combat(50,100);
            //System.out.println(message);
        }

    }

}
