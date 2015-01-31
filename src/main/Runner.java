package main;

import gui.GrapherDisplay;

public class Runner {
    public static void main(String[] args) {
        try {
            new GrapherDisplay().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
