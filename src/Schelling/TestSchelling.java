package Schelling;

import gui.GUISimulator;

import java.awt.*;

public class TestSchelling {
    public static void main(String[] args) {
        int width = 2480;//1350;
        int height = 1275;//690;
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);

        gui.setSimulable(new SchellingModels(width, height, 5, gui));
    }
}
