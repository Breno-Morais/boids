package Balls;

import gui.GUISimulator;

import java.awt.*;

public class Test_Cells_8imulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(500,500, Color.WHITE);

        gui.setSimulable(new Cells(500, 500, gui,2));
    }
}
