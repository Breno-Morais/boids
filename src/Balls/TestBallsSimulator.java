package Balls;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class TestBallsSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(500,500, Color.BLACK);

        gui.setSimulable(new BallsSimulator(gui));
    }
}