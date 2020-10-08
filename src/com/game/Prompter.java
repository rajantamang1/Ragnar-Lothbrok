package com.game;

import java.util.Scanner;

public class Prompter {

    public static void promptUser(){
        Country country = new Country();
        Scanner scanner= new Scanner(System.in);
        System.out.print("Make your next Move > ");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();
        if (userInput.equals("")){
            promptUser();
        }

        String[] inputArray = userInput.split(" ",2);

        if ((inputArray[0].equals("sail")) | (inputArray[0].equals("go"))){
            country.sail(inputArray[1]);
        }
    }


//    public static void main(String[] args) {
//        promptUser();
//    }
}
