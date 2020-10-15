package com.util;

import com.game.ApplicationRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.util.Statistics.setMessage;

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
                //setMessage(curCountry);
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
            setMessage("Can't find item");
            //System.out.println("Can't find item");
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
        /*if(item.equals("")){
            setMessage("You can see ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));
            //System.out.println("You can see ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));
        }else{
            setMessage("You can see ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));

            //System.out.println("You found ["+(stringBuilder.toString().substring(0,stringBuilder.length()-1)+ "]"));
        }*/

        return  stringBuilder.toString();
    }

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

            setMessage("That direction leads you to " + desCountry.strip());
            return curCountry;
        }else{
            setMessage("You are in " + desCountry);

            return desCountry;
        }

    }
    private static int getSoldierNumber(Document document, String country){
        int soldierCount;
        NodeList childNodes = document.getElementsByTagName(country).item(0).getChildNodes();
        soldierCount= Integer.parseInt(getValueByType(document, country,"soldiers").get(0).getTextContent());
        //int soldierCount=Integer.parseInt(childNodes1.item(1).getTextContent());
        return soldierCount;

    }
    private static int getWeaponNumber(Document document, String country){
        int weaponNumber;
        NodeList childNodes1 = document.getElementsByTagName(country).item(0).getChildNodes();
        weaponNumber = Integer.parseInt(getValueByType(document, country,"weapons").get(0).getTextContent());
        //weaponNumber = Integer.parseInt(childNodes1.item(5).getTextContent());
        return weaponNumber;
    }

    public static List<Node> getValueByType(Document document, String country, String noun){
        NodeList childNodes = document.getElementsByTagName(country).item(0).getChildNodes();
        List<Node> nodeList = new ArrayList<>();
        String value = "";
            for (int i= 0; i < childNodes.getLength(); i++){
                Node childNode = childNodes.item(i);
                if(childNode.getNodeType()==Node.ELEMENT_NODE) {
                    Element elem = (Element) childNode;
                    if (elem.getAttribute("type").equals(noun)||(elem.getAttribute("id").equals(noun))){
                        nodeList.add(elem);
                    }
                }
            }

        //int soldierCount=Integer.parseInt(childNodes.item(1).getTextContent());
        return nodeList;
    }
//    public static NodeList getValuesById(Document document, String country, String noun){
//
//    }


    // function to be used to get numbers of soldiers from current country and country to be attacked
    private static String combat(Document document, String attackCountry) throws NoSuchFieldException {
        CombatEngine combatEngine = new CombatEngine();
        String selectSoldierNumberDisplay = JOptionPane.showInputDialog(ApplicationRenderer.window,
                "How many soldiers, you want to attack with");
        int selectNumberOfSoldier = Integer.parseInt(selectSoldierNumberDisplay);
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
        int currentCombatPower = combatEngine.combatPower(selectNumberOfSoldier,curWeaponNumber);
        int desCombatPower = combatEngine.combatPower(desSoldierNumber,desWeaponsNumber);

        //checking battle outcome between two nations
        if (currentCombatPower>desCombatPower){
            result = attackCountry;
            //recruiting soldiers and extracting weapons from losing nation
            int recruitSoldierCount = (int)(desSoldierNumber*0.5);
            int lostSoldierCount = (int) (selectNumberOfSoldier*0.1);
            curCountrySoldierNumber += recruitSoldierCount;
            curCountrySoldierNumber = curCountrySoldierNumber-lostSoldierCount;
            int collectedWeaponNumber = (int)(desWeaponsNumber*0.5);
            curWeaponNumber += collectedWeaponNumber;
            //set the losing teams soldier to zero
            desSoldierNumber = 0;
            desWeaponsNumber = 0;

            /*String victorySign = getValueByType(document, result,"victorySign").get(0).getTextContent();
            if(victorySign.equals("vicSweden")){
                ApplicationRenderer.vicSweden.setVisible(true);
                System.out.println(victorySign);
            }*/

          /*  try{
                ApplicationRenderer.class.getDeclaredField(victorySign);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }*/

            //display on the main text area
            setMessage("You win!" + "You lost your own"+ lostSoldierCount+" soldiers. \nYou recruited "+ recruitSoldierCount
                    + " enemy soldiers. You have :" + curCountrySoldierNumber + " soldiers"
            + "\nYou have collected "+ collectedWeaponNumber+ ". Now, you have "+curWeaponNumber + " weapons");

        }else {
            result = "kattegat";
            //deducting soldiers and weapons from total from player
            int lostSoldierCount= (int)(selectNumberOfSoldier*0.25);
            curCountrySoldierNumber -=lostSoldierCount;
            int lostWeaponsNumber = (int)(curWeaponNumber *0.25);
            curWeaponNumber -= lostWeaponsNumber;

            //display message
            setMessage("You loose!"+"\nYou lost "+ lostSoldierCount+ " soldiers. You only have: "+
                    curCountrySoldierNumber+" soldiers."+"\nYou lost "+lostWeaponsNumber+ " weapons. Now, you have "+ curWeaponNumber+ " weapons.");

        }
        //updating number of soldiers and weapons according to the battle
        updateNumberOfSoldier(document, desSoldierNumber,attackCountry);
        updateNumberOfSoldier(document, curCountrySoldierNumber,"kattegat");
        updateWeaponNumber(document,desWeaponsNumber,attackCountry);
        updateWeaponNumber(document,curWeaponNumber,"kattegat");

       Statistics.setHeader("soldiers",String.valueOf(curCountrySoldierNumber));
        Statistics.setHeader("weapons",String.valueOf(curWeaponNumber));

        return result;
    }

    private static void updateNumberOfSoldier(Document document, int curCountrySoldierNumber,String countryName) {
        getValueByType(document, countryName,"soldiers").get(0)
                .setTextContent(String.valueOf(curCountrySoldierNumber));


        //document.getElementsByTagName(countryName).item(0).getChildNodes().item(1).
                //setTextContent(String.valueOf(curCountrySoldierNumber));
    }
    private static void updateWeaponNumber(Document document, int curWeaponNumber, String countryName){
        getValueByType(document, countryName,"weapons").get(0)
                .setTextContent(String.valueOf(curWeaponNumber));
        //document.getElementsByTagName(countryName).item(0).getChildNodes().item(5).
                //setTextContent(String.valueOf(curWeaponNumber));
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

            String item = textParser("pick", "horse", "kattegat", document);
            System.out.println(item);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

