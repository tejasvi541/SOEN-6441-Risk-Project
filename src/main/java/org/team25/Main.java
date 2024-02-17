package org.team25;

import org.team25.game.controllers.MapEditorController;
import org.team25.game.controllers.MapLoaderController;
import org.team25.game.controllers.ShowMapController;
import org.team25.game.models.map.GameMap;

public class Main {
    public static void main(String[] args) {

        GameMap gameMap = new GameMap();
        MapLoaderController load = new MapLoaderController();

        gameMap = load.readMap("Canada");

        ShowMapController showMapController = new ShowMapController(gameMap);
        showMapController.show();

        MapEditorController editor = new MapEditorController(gameMap);

        editor.run();

    }
}