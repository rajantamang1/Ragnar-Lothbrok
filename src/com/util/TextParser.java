package com.util;

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
import java.util.List;

public class TextParser {
    public static void textParser(String noun){
        try {


            File file = new File("Ragnar-Lothbrok/files/test.xml");

            DocumentBuilderFactory mybuilder = DocumentBuilderFactory.newInstance();
            DocumentBuilder mydocbuilder = mybuilder.newDocumentBuilder();
            Document document = mydocbuilder.parse(file);
            document.getDocumentElement().normalize();
            System.out.println("Root element:"+ document.getDocumentElement().getNodeName());//gives you country
            NodeList nodeList = document.getElementsByTagName(noun);
            List<String> result = new ArrayList<>();
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
                //NodeList nodeList1 = element.getElementsByTagName("property");

//                for (int j =0; j<nodeList1.getLength();j++){
//                    Node node1 = nodeList1.item(j);
//                    Element elementProperty = (Element) node1;
//                    System.out.println(elementProperty.getAttribute("type"));
//                }

                //System.out.println(element.getElementsByTagName());

            }

            System.out.println(result);
        } catch (Exception e){
            e.printStackTrace();
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
        textParser("kattegat");
    }
}

