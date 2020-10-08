package com.game;

import java.util.Scanner;

public class Prompter {

    public static String promptUser(Country country){
        //Country country = new Country();
        Scanner scanner= new Scanner(System.in);
        System.out.print("Make your next Move > ");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();
        if (userInput.equals("")){
            promptUser(country);
        }

        String[] inputArray = userInput.split(" ",2);
        String newCountry = "";
        if ((inputArray[0].equals("sail")) | (inputArray[0].equals("go"))){
            newCountry = country.sail(inputArray[1]);



        }
        return newCountry;
    }


//    public static void main(String[] args) {
//        promptUser();
//    }
}
