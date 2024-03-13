package org.team21.game.models.orders;

import org.team21.game.models.map.GameMap;

public class Blockade extends Order{
    private final GameMap d_GameMap;

    public Blockade(){
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }
}
