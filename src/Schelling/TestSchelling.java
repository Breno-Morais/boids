package Schelling;

import gui.GUISimulator;

import java.awt.*;

public class TestSchelling {
    public static void main(String[] args) {
        int width = 1350;
        int height = 690;
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);

        gui.setSimulable(new SchellingModels(width, height, 5, gui));
    }
}
