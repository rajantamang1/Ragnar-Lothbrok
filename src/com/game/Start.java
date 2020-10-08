package com.game;

import com.util.CombatEngine;

public class Start {

    public static void newGame(){
        Country country = new Country();
        CombatEngine combatEngine = new CombatEngine();
        String message="";
        while (true){
            Prompter.promptUser();
            //message=combatEngine.combat(50,100);
            //System.out.println(message);

        }

    }

}
