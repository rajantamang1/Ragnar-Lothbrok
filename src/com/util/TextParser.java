package com.util;

import com.game.Country;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextParser {

    public static String textParser(String cmd, String noun, String countryName,Document document){
        String curCountry = "";
        String itemList = "";
        String result = "";

        try {
//            File file = new File("Ragnar-lothbrok/files/test.xml");
//
//            DocumentBuilderFactory mybuilder = DocumentBuilderFactory.newInstance();
//            DocumentBuilder mydocbuilder = mybuilder.newDocumentBuilder();
//            Document document = mydocbuilder.parse(file);
//            document.getDocumentElement().normalize();
            System.out.println("Root element:"+ document.getDocumentElement().getNodeName());//gives you country
            NodeList nodeList = document.getElementsByTagName(noun);

            //Map<String, String> countryInstance = new HashMap<>();

            //Country curCountryObject = null;
            if (cmd.equals("sail")){
               curCountry = direction(document, noun, countryName);
                //countryInstance = new Country(curCountry);
                 //countryInstance.setNameOfCountry(curCountry);
                 result = curCountry;
               //countryInstance.put("","");
               System.out.println(curCountry);
            }else if(cmd.equals("look")){
                itemList=viewItems(document,countryName, "");
                result = itemList;
            }else if(cmd.equals("inspect")) {
                itemList=viewItems(document,countryName, noun);
                result = itemList;
            }else if(cmd.equals("pick")){
                result = grab(document, noun, countryName);
            }else if (cmd.equals("attack")){
                result = combat(document,countryName);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    private static String grab(Document document, String item, String curCountry){
        NodeList countryNodeList = document.getElementsByTagName(curCountry);
        NodeList childNodes = null;
        String itemFound = "";
        StringBuilder strBld = new StringBuilder();
        if (document.getElementsByTagName(curCountry) != null){
            Node countryNode = countryNodeList.item(0);
            childNodes = countryNode.getChildNodes();
        }else{
            System.out.println("it is null");
        }
        for (int i= 0; i < childNodes.getLength(); i++){

            Node childNode = childNodes.item(i);
            if(childNode.getNodeType()==Node.ELEMENT_NODE) {

                Element elem = (Element) childNode;
                if(elem.getAttribute("type").equals(item) ){
                    itemFound = strBld.append(item)
                            .append(",")
                            .append(elem.getTextContent())
                            .toString();
                    break;
                }
            }
        }
        if (itemFound.equals("")){
            System.out.println("Can't find item");
        }
        return itemFound;
    }
    private static String viewItems(Document document, String curCountry, String item) {

        NodeList countryNodeList = document.getElementsByTagName(curCountry);
        NodeList childNodes = null;
        String inspectItem = "";
        for(int j=0; j < countryNodeList.getLength(); j++){
            if (document.getElementsByTagName(curCountry) != null){
                //for(int j=0; j < countryNodeList.getLength();j++){
                Node countryNode = countryNodeList.item(j);
                childNodes = countryNode.getChildNodes();
                //}
            }else{
                System.out.println("it is null");
            }
        }


        // NodeList sailNodeList = document.getElementsByTagName("direction");
        String desCountry = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i= 0; i < childNodes.getLength(); i++) {

            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) childNode;

                if (item.equals("")) {
                    if (elem.getTagName().equals("property")) {
                        stringBuilder.append(elem.getAttribute("type"))
                                .append(",");
                    }
                } else {

                    if (elem.getTagName().equals("property") && elem.getAttribute("id").equals("inspectable")
                    && elem.getAttribute("type").equals(item)) {
                        NodeList inspectedItem = elem.getElementsByTagName("inner");
                        for (int k =0; k < inspectedItem.getLength();k++){
                            Element element = (Element)inspectedItem.item(k);
                            //System.out.println("children :" + element.getAttribute("type"));
                            stringBuilder.append(element.getAttribute("type"))
                                    .append(",");
                        }
                    }
                }
            }

        }

        //System.out.println(stringBuilder.length());
        if(item.equals("")){
            System.out.println("You can see ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));
        }else{
            System.out.println("You found ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));
        }

        return  stringBuilder.toString();
    }

    /*private static Country initializeCountry(Document document, String curCountry) {
        NodeList countryNodeList = document.getElementsByTagName(curCountry);
        NodeList childNodes = null;
        if (document.getElementsByTagName(curCountry) != null) {
            Node countryNode = countryNodeList.item(0);
            childNodes = countryNode.getChildNodes();
        } else {
            System.out.println("it is null");
        }
        // boolean isHorseAvailable, boolean isFlokkiFound,int treasures, List<String>weapons, List<String> direction)
        String desCountry = "";
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) childNode;
                if (elem.getAttribute("type").equals("soldiers")) {
                    desCountry = elem.getTextContent();
                    String nameOfCountry =
                    int noOfSoldiers
                    int treasures
                    boolean isHorseAvailable
                    boolean isFlokkiFound
                    int treasures
                    List<String>weapons
                    List<String> direction
                    break;
                }
            }


        }
        return null;
    }*/ //Country(String nameOfCountry, int noOfSoldiers, boolean isBoatsAvailable,

    //calls this function for sailing, travelling to different countries
    private static String direction(Document document, String noun, String curCountry){

        NodeList countryNodeList = document.getElementsByTagName(curCountry);
        NodeList childNodes = null;

        if (document.getElementsByTagName(curCountry) != null){
            //for(int j=0; j < countryNodeList.getLength();j++){
                Node countryNode = countryNodeList.item(0);
                childNodes = countryNode.getChildNodes();
            //}
        }else{
            System.out.println("it is null");
        }


       // NodeList sailNodeList = document.getElementsByTagName("direction");
        String desCountry = "";
        for (int i= 0; i < childNodes.getLength(); i++){
            Node childNode = childNodes.item(i);
            if(childNode.getNodeType()==Node.ELEMENT_NODE) {
                Element elem = (Element) childNode;
                if (elem.getAttribute("type").equals(noun)) {
                    desCountry = elem.getTextContent();
                    break;
                }
            }
        }
        if (desCountry.strip().equals("nowhere")){
            System.out.println("Your are in " +curCountry);
            return curCountry;
        }else{
            System.out.println("You are in " + desCountry);

            return desCountry;
        }

    }
    private static int getSoldierNumber(Document document, String country){
        NodeList childNodes1 = document.getElementsByTagName(country).item(0).getChildNodes();
        int soldierCount=Integer.parseInt(childNodes1.item(1).getTextContent());
        return soldierCount;

    }

    private static int getWeaponNumber(Document document, String country){
        int weaponNumber;
        NodeList childNodes1 = document.getElementsByTagName(country).item(0).getChildNodes();
        weaponNumber = Integer.parseInt(childNodes1.item(5).getTextContent());
        return weaponNumber;
    }
    // function to be used to get numbers of soldiers from current country and country to be attacked
    private static String combat(Document document, String attackCountry){
        CombatEngine combatEngine = new CombatEngine();
        int desSoldierNumber ;
        int curCountrySoldierNumber;
        int desWeaponsNumber;
        int curWeaponNumber;
        String result;
        String apple;

        desSoldierNumber=getSoldierNumber(document,attackCountry);

        desWeaponsNumber=getWeaponNumber(document,attackCountry);
        System.out.println("the weapons"+desWeaponsNumber);
        curCountrySoldierNumber = getSoldierNumber(document,"kattegat");
        curWeaponNumber = getWeaponNumber(document,"kattegat");
        System.out.println("You are in war");
        int currentCombatPower = combatEngine.combatPower(curCountrySoldierNumber,curWeaponNumber);
        int desCombatPower = combatEngine.combatPower(desSoldierNumber,desWeaponsNumber);

        if (currentCombatPower>desCombatPower){
            System.out.println("You win!");
            result = attackCountry;
            int recruitSoldierCount = (int)(desSoldierNumber*0.5);
            curCountrySoldierNumber += recruitSoldierCount;
            int collectedWeaponNumber = (int)(desWeaponsNumber*0.5);
            curWeaponNumber += collectedWeaponNumber;

            System.out.println("You recruited "+ recruitSoldierCount
                    + " enemy soldiers. You have :" + curCountrySoldierNumber + " soldiers");
            System.out.println("You have collected "+ collectedWeaponNumber+ ". Now, you have "+curWeaponNumber + " weapons");
            updateNumberOfSoldier(document, curCountrySoldierNumber,"kattegat");
            updateWeaponNumber(document,curWeaponNumber,"kattegat");
        }else {
            System.out.println("You loose!");
            result = "kattegat";
            int lostSoldierCount= (int)(curCountrySoldierNumber*0.25);
            curCountrySoldierNumber -=lostSoldierCount;
            int lostWeaponsNumber = (int)(curWeaponNumber *0.25);
            curWeaponNumber -= lostWeaponsNumber;
            System.out.println("You lost "+ lostSoldierCount+ " soldiers. You only have: "+
                    curCountrySoldierNumber+" soldiers.");
            System.out.println("You lost "+lostWeaponsNumber+ " weapons. Now, you have "+ curWeaponNumber+ " weapons.");

            updateNumberOfSoldier(document, curCountrySoldierNumber,"kattegat");
            updateWeaponNumber(document,curWeaponNumber,"kattegat");
        }
        return result;
    }

    private static void updateNumberOfSoldier(Document document, int curCountrySoldierNumber,String countryName) {
        document.getElementsByTagName(countryName).item(0).getChildNodes().item(1).
                setTextContent(String.valueOf(curCountrySoldierNumber));
    }
    private static void updateWeaponNumber(Document document, int curWeaponNumber, String countryName){
        document.getElementsByTagName(countryName).item(0).getChildNodes().item(5).
                setTextContent(String.valueOf(curWeaponNumber));
    }

    public static List<String> trim(String word){
        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(word));
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String lines;
            while ((lines= reader.readLine())!= null){
                list.add(lines.trim());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return stringBuffer.toString();
        return list;
    }
    public static void main(String[] args) {
       // textParser("kattegat");
//        String item = textParser("look", "", "kattegat", new Country());
//
//        String item1 = textParser("pick", "horse", "kattegat", new Country());
//        System.out.println(item);
//        System.out.println(item1);
        //System.out.println(textParser("attack", "", "sweden", new Country()));
        try {
            File file = new File("Ragnar-lothbrok/files/test.xml");

            DocumentBuilderFactory mybuilder = DocumentBuilderFactory.newInstance();
            DocumentBuilder mydocbuilder = mybuilder.newDocumentBuilder();
            Document document = mydocbuilder.parse(file);
            document.getDocumentElement().normalize();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

