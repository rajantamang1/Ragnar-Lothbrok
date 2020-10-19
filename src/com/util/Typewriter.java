package com.util;

import com.game.ApplicationRenderer;

import javax.swing.*;

public class Typewriter {
    private Timer timer;
    private int characterIndex = 0;
    private String input;
    private JTextArea textArea;

    public Typewriter(JTextArea textArea, String input) {
        this.textArea = textArea;
        this.input = input;
        timer = new Timer(10, e -> {
            if (characterIndex < input.length()) {
                if (String.valueOf(input.charAt(characterIndex)).equals(";")){
                    textArea.append("\n");
                }else {
                    textArea.append(Character.toString(input.charAt(characterIndex)));
                    //characterIndex++;
                }
                characterIndex++;
            } else {
                stop();
            }
        });
    }

    public void start() {
        textArea.setText(null);
        characterIndex = 0;
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public Timer getTimer() {
        return timer;
    }
}
