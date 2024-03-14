package org.team21.game.controllers;

import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;


/**
 * This is the Controller to show the full Map in the terminal
 * @author Tejasvi
 */
public class ShowMapController {
    /**
     * The d_GameMap is game map.
     */
    private GameMap d_GameMap;

    /**
     * Constructor to set the current GameMap object to read and show the map
     * @param d_GameMap GMap Object Parameter
     */
    public ShowMapController(GameMap d_GameMap){
        this.d_GameMap = GameMap.getInstance();
    }

    /**
     * This Function is the main logic behind displaying the Continents, then their corresponding countries and then their corresponding neighbors
     */
    public void show() {
        if (d_GameMap.getMapName() == null || d_GameMap.getContinents() == null || d_GameMap.getMapName() == "" || d_GameMap == null){
            System.out.println("Any Map is not Loaded");
            return;
        }
        System.out.printf("%100s\n", "===============================================================================================================================");
        System.out.printf(Constants.SPACE_FORMATTER, "Continents", "Country", "Players",  "Armies", "Country's Neighbors");
        System.out.printf("%100s\n", "===============================================================================================================================");
        boolean l_isContinentPrinting = true;
        boolean l_isCountryPrinting = true;
        for(Continent l_continent : d_GameMap.getContinents().values()) {
            if(l_continent.getCountries().isEmpty()) {
                System.out.printf(Constants.SPACE_FORMATTER, l_continent.getContinentId(), "", "", "", "");
            }
            for(Country l_country : l_continent.getCountries().values()) {
                if(l_country.getNeighbours().isEmpty()) {
                    if (l_isCountryPrinting) {
                        String continentId = l_isContinentPrinting ? l_continent.getContinentId() : "i";
                        System.out.printf(Constants.SPACE_FORMATTER, continentId, l_country.getCountryId(), l_country.getPlayer().getName(), l_country.getArmies(), "");
                        l_isCountryPrinting = false;
                        l_isContinentPrinting = false;
                    }
                }
                for(Country l_neighbor : l_country.getNeighbours().values()) {
                    if (l_isCountryPrinting) {
                        String continentId = l_isContinentPrinting ? l_continent.getContinentId() : "";
                        System.out.printf(Constants.SPACE_FORMATTER, continentId, l_country.getCountryId(), l_country.getPlayer().getName(), l_country.getArmies(), l_neighbor.getCountryId());
                        l_isCountryPrinting = false;
                        l_isContinentPrinting = false;
                    }
                    else {
                        System.out.printf(Constants.SPACE_FORMATTER, "", "", "", "", l_Neighbor.getCountryId());
                    }
                }
                l_IsCountryPrinting = true;
            }
            l_IsContinentPrinting = true;
            l_IsCountryPrinting = true;
        }
    }

}
