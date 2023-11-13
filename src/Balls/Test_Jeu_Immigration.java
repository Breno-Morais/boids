package Balls;

import gui.GUISimulator;

import java.awt.*;

public class Test_Jeu_Immigration {


        public static void main(String[] args) {
            GUISimulator gui = new GUISimulator(500,500, Color.WHITE);

            gui.setSimulable(new Jeu_Immigration(1000, 1000, gui, 4));
        }
    }


