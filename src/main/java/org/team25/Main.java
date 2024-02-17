package org.team25;
import org.team25.game.game_engine.GameEngine;
import org.team25.game.utils.Constants;

public class Main {
    public static void main(String[] args) {

        System.out.println(Constants.WELCOME_MESSAGE);
        new GameEngine().start();

    }
}