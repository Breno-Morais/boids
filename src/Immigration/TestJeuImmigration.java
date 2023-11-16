package Immigration;

import gui.GUISimulator;

import java.awt.*;


public class TestJeuImmigration {
    public static void main(String[] args) {
        int width = 2480;//1350;
        int height = 1275;//690;
        GUISimulator gui = new GUISimulator(width,height, Color.WHITE);

        gui.setSimulable(new JeuImmigration(width, height, 5, gui));
    }
}
