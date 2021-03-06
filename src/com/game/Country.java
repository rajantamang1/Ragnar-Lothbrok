package com.game;

import com.util.TextParser;
import org.w3c.dom.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Country {

    // attributes or fields
    private String nameOfCountry="kattegat";

    /*private int noOfSoldiers = 300;
    private boolean isBoatsAvailable = false;
    private boolean isHorseAvailable = false;
    private boolean isFlokkiFound = false;
    private int treasures = 0; // can include coin e.g. gold coins
    private boolean weapons = false;
    private String direction;*/

    //constructor
    public Country(){
        // no args
    }
    public Country(String nameOfCountry){
        this();
        this.setNameOfCountry(nameOfCountry);
    }
   /* public Country(String nameOfCountry, int noOfSoldiers, boolean isBoatsAvailable, boolean isHorseAvailable, boolean isFlokkiFound,int treasures, boolean weapons, String direction){
        this.nameOfCountry=nameOfCountry;
        this.noOfSoldiers=noOfSoldiers;
        this.isBoatsAvailable=isBoatsAvailable;
        this.isHorseAvailable=isHorseAvailable;
        this.isFlokkiFound=isFlokkiFound;
        this.treasures=treasures;
        this.weapons=weapons;
        this.direction=direction;
    }*/

    //business methods
    public String inspect(String item,String curCountry, Document document){
        String list = TextParser.textParser("inspect",item,curCountry, document);
        return list;
    }
    public String getItem(String item, String curCountry, Document document){
        String itemPicked = TextParser.textParser("pick",item, curCountry, document);
        return itemPicked;
    }

    public String sail(String direction, Document document){
        String country = TextParser.textParser("sail", direction, nameOfCountry, document);
        return country;
    }

    public String look(Document document){
         String list = TextParser.textParser("look","",nameOfCountry, document);
        return list;
    }
    public String attack(String currentCountry, Document document){
        String result = TextParser.textParser("attack","",nameOfCountry,document);
        return result;
    }
    public void flee(Document document){
        TextParser.textParser("flee", "",nameOfCountry,document);
    }


    public String getNameOfCountry() {
        return nameOfCountry;
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }



















    // getters and setters to access private fields
   /* public String getNameOfCountry() {
        return nameOfCountry;
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }

    public int getNoOfSoldiers() {
        return noOfSoldiers;
    }

    public void setNoOfSoldiers(int noOfSoldiers) {
        this.noOfSoldiers = noOfSoldiers;
    }

    public boolean isBoatsAvailable() {
        return isBoatsAvailable;
    }

    public void setBoatsAvailable(boolean boatsAvailable) {
        isBoatsAvailable = boatsAvailable;
    }

    public boolean isHorseAvailable() {
        return isHorseAvailable;
    }

    public void setHorseAvailable(boolean horseAvailable) {
        isHorseAvailable = horseAvailable;
    }

    public boolean isFlokkiFound() {
        return isFlokkiFound;
    }

    public void setFlokkiFound(boolean flokkiFound) {
        isFlokkiFound = flokkiFound;
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int treasures) {
        this.treasures = treasures;
    }

    public boolean getWeapons() {
        return weapons;
    }

    public void setWeapons(boolean weapons) {
        this.weapons = weapons;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Country{" +
                "nameOfCountry='" + nameOfCountry + '\'' +
                ", noOfSoldiers=" + noOfSoldiers +
                ", isBoatsAvailable=" + isBoatsAvailable +
                ", isHorseAvailable=" + isHorseAvailable +
                ", isFlokkiFound=" + isFlokkiFound +
                ", treasures=" + treasures +
                ", weapons=" + weapons +
                ", direction=" + direction +
                ", itemsCollection=" + itemsCollection +
                '}';
    }*/
}
