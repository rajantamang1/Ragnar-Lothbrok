package com.game;

import com.util.CombatEngine;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Start {

    public static void newGame() {
        try {
            File file = new File("Ragnar-lothbrok/files/test.xml");
            DocumentBuilderFactory mybuilder = DocumentBuilderFactory.newInstance();
            DocumentBuilder mydocbuilder = mybuilder.newDocumentBuilder();
            Document document = mydocbuilder.parse(file);
            document.getDocumentElement().normalize();

            Country country = new Country();
            Set<String> defeatedCountry = new HashSet<>();
            CombatEngine combatEngine = new CombatEngine();
            String curCountry = "kattegat";
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
                    curCountry = country.sail(inputArray[1], document);
                    country.setNameOfCountry(curCountry);

                } else if (inputArray[0].equals("look") | inputArray[0].equals("overlook") | inputArray[0].equals("observe") | inputArray[0].equals("view")) {
                    country.look(document);

                } else if (inputArray[0].equals("pick") | inputArray[0].equals("get")) {
                    String[] keyValue = country.getItem(inputArray[1], curCountry,document).split(",");
                    if (keyValue.length == 7) {
                        inventoryMap.put(keyValue[0], keyValue[1]);
                    }
                } else if (inputArray[0].equals("inspect") | inputArray[0].equals("check") | inputArray[0].equals("investigate") | inputArray[0].equals("examine")) {
                    country.inspect(inputArray[1], curCountry,document);
                } else if (inputArray[0].equals("attack") | inputArray[0].equals("engage")) {

                    if(!curCountry.equals("kattegat")||(!curCountry.equals("iceland"))){
                        String countryAttacked = country.attack(curCountry,document);

                        if (!countryAttacked.equals("kattegat")) {
                            defeatedCountry.add(countryAttacked);
                        }

                    }else{
                        System.out.println("you cannot attack!!");
                    }
                    //String message = combatEngine.combat(50, 100, 20, 30);
                    //System.out.println(message);
                }
                System.out.println(defeatedCountry);
            if(defeatedCountry.size()==2){
                System.out.println("All Hail! King Ragnar. You are the mighty King of the King.");
                break;
            }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
