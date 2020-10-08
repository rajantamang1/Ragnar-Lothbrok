package com.util;

import com.game.Country;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextParser {
    public static String textParser(String cmd, String noun, String countryName, Country countryInstance){
        String curCountry = "";
        try {


            File file = new File("Ragnar-lothbrok/files/test.xml");

            DocumentBuilderFactory mybuilder = DocumentBuilderFactory.newInstance();
            DocumentBuilder mydocbuilder = mybuilder.newDocumentBuilder();
            Document document = mydocbuilder.parse(file);
            document.getDocumentElement().normalize();
            System.out.println("Root element:"+ document.getDocumentElement().getNodeName());//gives you country
            NodeList nodeList = document.getElementsByTagName(noun);

            //Map<String, String> countryInstance = new HashMap<>();

            //Country curCountryObject = null;
            if (cmd.equals("sail")){
               curCountry = direction(document, noun, countryName);
                //countryInstance = new Country(curCountry);
                 countryInstance.setNameOfCountry(curCountry);

               //countryInstance.put("","");
               System.out.println(curCountry);
            }

            /*List<String> result = new ArrayList<>();
            for(int i = 0; i <nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    //System.out.println(element.getAttribute("type"));

                    result = trim(element.getElementsByTagName("property").item(i).getTextContent());
                    //System.out.println(element.getElementsByTagName("direction").item(0).getTextContent());
                }
                for (int j =0; j<node.getChildNodes().getLength();j++ ) {
                    System.out.println(node.getChildNodes().item(j).getNodeName());

                }
            }
            System.out.println(result);*/

        } catch (Exception e){
            e.printStackTrace();
        }

        return curCountry;
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
        //Country(String nameOfCountry, int noOfSoldiers, boolean isBoatsAvailable,
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
    }*/
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
            System.out.println("current country1: " +curCountry);
            return curCountry;
        }else{
            System.out.println("current country2: " + desCountry);

            return desCountry;
        }

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
        System.out.println(textParser("sail", "east", "kattegat", new Country()));

        System.out.println(textParser("sail", "east", "russia", new Country()));
    }
}

