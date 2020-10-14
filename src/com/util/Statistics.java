package com.util;


import static com.game.ApplicationRenderer.getMainTxtArea;
import static com.game.ApplicationRenderer.getSoldierCountNumberLabel;
import static com.game.ApplicationRenderer.getWeaponCountNumberLabel;
import javax.swing.JTextArea;

public class Statistics {
    //private static ApplicationRenderer aR;
    public static void setMessage(String message){
        JTextArea messageBox = getMainTxtArea();
        messageBox.setText(message);
    }

    public static void setHeader(String headerType, String stats){
        if (headerType.equals("soldiers")){
            getSoldierCountNumberLabel().setText(stats);
        }else if(headerType.equals("weapons")){
            getWeaponCountNumberLabel().setText(stats);
        }
    }
}
