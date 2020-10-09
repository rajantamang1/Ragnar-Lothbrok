package com.game;

import com.util.CombatEngine;

import java.util.Scanner;

public class Start {

    public static void newGame(){
        Country country = new Country();
        CombatEngine combatEngine = new CombatEngine();
        String curCountry = country.getNameOfCountry();

        while (true){
            String userInput="";
            while (userInput.equals("")){
                Scanner scanner= new Scanner(System.in);
                System.out.print("Make your next Move > ");
                userInput = scanner.nextLine();
            }
            userInput = userInput.toLowerCase();
            String[] inputArray = userInput.split(" ",2);
            String newCountry = "";
            String itemsVisible = "";
            if ((inputArray[0].equals("sail")) | (inputArray[0].equals("go"))) {
                newCountry = country.sail(inputArray[1]);
                country.setNameOfCountry(newCountry);


            }else if((inputArray[0].equals("look"))|(inputArray[0].equals("overlook"))){
                newCountry=country.look();

            }




            String message = combatEngine.combat(50, 100);
            System.out.println(message);
        }

    }

}
