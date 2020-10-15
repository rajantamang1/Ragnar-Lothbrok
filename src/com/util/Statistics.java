package com.util;

import static com.game.ApplicationRenderer.getMainTxtArea;
import static com.game.ApplicationRenderer.getSoldierCountNumberLabel;
import static com.game.ApplicationRenderer.getWeaponCountNumberLabel;
import javax.swing.JTextArea;
import java.util.Arrays;

public class Statistics {
    //private static ApplicationRenderer aR;
    public static void setMessage(String message){
        /*JTextArea messageBox = getMainTxtArea();
        messageBox.setText(message);*/
        typewriter(message);
    }
    private static void typewriter(String message){
        System.out.println(message);
        JTextArea messageBox = getMainTxtArea();
        getMainTxtArea().setText("");
        String[] messageList = message.split(";");
        System.out.println(messageList.length);
        //Arrays.stream(messageList).forEach(m -> System.out.println(m));
        //System.out.println(messageList[0]);

        for (String m: messageList
             ) {
            messageBox.append(m + "\n");
            //System.out.println(m);
        }
    }

    public static void setHeader(String headerType, String stats){
        if (headerType.equals("soldiers")){
            getSoldierCountNumberLabel().setText(stats);
        }else if(headerType.equals("weapons")){
            getWeaponCountNumberLabel().setText(stats);
        }
    }
}
