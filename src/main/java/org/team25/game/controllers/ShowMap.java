package org.team25.game.controllers;

//import org.apache.logging.log4j.LogManager;
import org.team25.game.models.Continent;
import org.team25.game.models.Country;
import org.team25.game.models.GameMap;

//import java.util.logging.Level;
//import java.util.logging.Logger;


public class ShowMap {

    private GameMap d_gameMap;

    /**
     * Constructor to set the current GMap object to read and show the map
     * @param p_gameMap GMap Object Parameter
     */
    public ShowMap(GameMap p_gameMap){
        this.d_gameMap = p_gameMap;
    }
//    /**
//     * A data member that will log the data for the class
//     */
//    private static final Logger d_Logger = (Logger) LogManager.getLogger(ShowMap.class);
//
//    /**
//     * A data member to set the log level
//     */
//    Level d_LogLevel=Level.parse("INFO");
    public void show(GameMap p_gameMap){
//        d_Logger.log(d_LogLevel, p_gameMap.get_continents().values());
        for(Continent l_continent:p_gameMap.get_continents().values()){
            System.out.println(l_continent.get_continentId());
        }
    }

}
