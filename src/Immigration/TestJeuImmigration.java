package Immigration;

import gui.GUISimulator;

import java.awt.*;


public class TestJeuImmigration {
    public static void main(String[] args) {
        int width = 880;
        int height = 500;
        GUISimulator gui = new GUISimulator(width,height, Color.WHITE);

        gui.setSimulable(new JeuImmigration(width, height, 5, gui));
    }
}
