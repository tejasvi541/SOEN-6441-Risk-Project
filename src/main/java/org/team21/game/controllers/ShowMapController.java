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
        boolean l_IsContinentPrinting = true;
        boolean l_IsCountryPrinting = true;
        for(Continent l_Continent : d_GameMap.getContinents().values()) {
            if(l_Continent.getCountries().isEmpty()) {
                System.out.printf(Constants.SPACE_FORMATTER, l_Continent.getContinentId(), "", "", "", "");
            }
            for(Country l_Country : l_Continent.getCountries().values()) {
                if(l_Country.getNeighbours().isEmpty()) {
                    if(l_IsContinentPrinting && l_IsCountryPrinting) {
                        System.out.printf(Constants.SPACE_FORMATTER, l_Continent.getContinentId(), l_Country.getCountryId(), l_Country.getPlayer().getName(), l_Country.getArmies(), "");
                        l_IsContinentPrinting = false;
                        l_IsCountryPrinting = false;
                    }
                    else if(l_IsCountryPrinting) {
                        System.out.printf(Constants.SPACE_FORMATTER, "", l_Country.getCountryId(), l_Country.getPlayer().getName(), l_Country.getArmies(), "");
                        l_IsCountryPrinting =  false;
                    }
                }
                for(Country l_Neighbor : l_Country.getNeighbours().values()) {
                    if(l_IsContinentPrinting && l_IsCountryPrinting) {
                        System.out.printf(Constants.SPACE_FORMATTER, l_Continent.getContinentId(), l_Country.getCountryId(), l_Country.getPlayer().getName(), l_Country.getArmies(), l_Neighbor.getCountryId());
                        l_IsContinentPrinting = false;
                        l_IsCountryPrinting = false;
                    }
                    else if(l_IsCountryPrinting) {
                        System.out.printf(Constants.SPACE_FORMATTER, "", l_Country.getCountryId(), l_Country.getPlayer().getName(), l_Country.getArmies(), l_Neighbor.getCountryId());
                        l_IsCountryPrinting = false;
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
