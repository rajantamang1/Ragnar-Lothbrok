package com.game;

import com.util.CombatEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Start {

    public static void newGame() {
        Country country = new Country();
        CombatEngine combatEngine = new CombatEngine();
        String curCountry = country.getNameOfCountry();
        Map<String, String> inventoryMap = new HashMap<>();
        while (true) {
            System.out.println("Your possessions: " + inventoryMap.keySet());
            String userInput = "";
            while (userInput.equals("")) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Make your next Move > ");
                userInput = scanner.nextLine();
            }
            userInput = userInput.toLowerCase();
            String[] inputArray = userInput.split(" ", 2);
            //String newCountry = "";
            String itemsVisible = "";
            if ((inputArray[0].equals("sail")) | (inputArray[0].equals("go"))) {
                curCountry = country.sail(inputArray[1]);
                country.setNameOfCountry(curCountry);


            } else if (inputArray[0].equals("look") | inputArray[0].equals("overlook") | inputArray[0].equals("observe") | inputArray[0].equals("view")) {
                country.look();

            } else if (inputArray[0].equals("pick") | inputArray[0].equals("get")) {
                String[] keyValue = country.getItem(inputArray[1], curCountry).split(",");
                if (keyValue.length == 2) {
                    inventoryMap.put(keyValue[0], keyValue[1]);
                }
            } else if (inputArray[0].equals("inspect") | inputArray[0].equals("check") | inputArray[0].equals("investigate") | inputArray[0].equals("examine")) {
                country.inspect(inputArray[1], curCountry);
            } else if (inputArray[0].equals("attack") | inputArray[0].equals("engage")) {
                country.inspect(inputArray[1], curCountry);


                String message = combatEngine.combat(50, 100, 20, 30);
                System.out.println(message);
            }

        }
    }
}
