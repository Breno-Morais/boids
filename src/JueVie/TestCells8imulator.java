package JueVie;

import gui.GUISimulator;

import java.awt.*;

public class TestCells8imulator {
    public static void main(String[] args) {
        int width = 1360;
        int height = 700;
        GUISimulator gui = new GUISimulator(width,height, Color.WHITE);

        gui.setSimulable(new Cells(width, height, 5, gui));
    }
}
