package JueVie;

import gui.GUISimulator;

import java.awt.*;

public class TestCells8imulator {
    public static void main(String[] args) {
        int width = 2480;//1350;
        int height = 1275;//690;
        GUISimulator gui = new GUISimulator(width,height, Color.WHITE);

        gui.setSimulable(new Cells(width, height, 5, gui));
    }
}
