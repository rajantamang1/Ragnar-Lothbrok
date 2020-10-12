package com.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StarterPage {
    public static void startPage(){
        Path banner = Paths.get("Ragnar-lothbrok/files/Banner1.txt");
        try (Stream<String> lines = Files.lines(banner)) {
            lines.forEach(s -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            });
        } catch (IOException ex) {
            // do something or re-throw...
        }
    }

    public static void main(String[] args) {
        startPage();
    }
}
