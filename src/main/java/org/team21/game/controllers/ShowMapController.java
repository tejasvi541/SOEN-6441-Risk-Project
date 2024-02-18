package org.team21.game.controllers;

import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;


/**
 * This is the Controller to show the full Map in the terminal
 * @author Tejasvi
 */
public class ShowMapController {

    private GameMap d_gameMap;

    /**
     * Constructor to set the current GameMap object to read and show the map
     * @param d_gameMap GMap Object Parameter
     */
    public ShowMapController(GameMap d_gameMap){
        this.d_gameMap = GameMap.getInstance();
    }

    /**
     * This Function is the main logic behind displaying the Continents, then their corresponding countries and then their corresponding neighbors
     */
    public void show() {
        if (d_gameMap.get_mapName() == null || d_gameMap.get_continents() == null || d_gameMap.get_mapName() == "" || d_gameMap == null){
            System.out.println("Any Map is not Loaded");
            return;
        }
        System.out.printf("%100s\n", "===============================================================================================================================");
        System.out.printf("%30s%55s%40s\n", "Continents", "Country  Players  Armies", "Country's Neighbors");
        System.out.printf("%100s\n", "===============================================================================================================================");
        boolean l_isContinentPrinting = true;
        boolean l_isCountryPrinting = true;
        for(Continent l_continent : d_gameMap.get_continents().values()) {
            if(l_continent.get_countries().isEmpty()) {
                System.out.printf("\n%25s%25s%25s\n", l_continent.get_continentId(), "", "");
            }
            for(Country l_country : l_continent.get_countries().values()) {
                if(l_country.get_Neighbours().isEmpty()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", l_continent.get_continentId(), l_country.get_countryId() + l_country.getPlayer().getName()  + l_country.getArmies(), "");
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", "", l_country.get_countryId() + l_country.getPlayer().getName() +l_country.getArmies(), "");
                        l_isCountryPrinting =  false;
                    }
                }
                for(Country l_neighbor : l_country.get_Neighbours().values()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", l_continent.get_continentId(), l_country.get_countryId()+ "  "+ l_country.getPlayer().getName() + "  " + l_country.getArmies(), l_neighbor.get_countryId());
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", "", l_country.get_countryId()+ "  "+ l_country.getPlayer().getName() + "  " + l_country.getArmies(), l_neighbor.get_countryId());
                        l_isCountryPrinting = false;
                    }
                    else {
                        System.out.printf("%35s%35s%50s\n", "", "", l_neighbor.get_countryId());
                    }
                }
                l_isCountryPrinting = true;
            }
            l_isContinentPrinting = true;
            l_isCountryPrinting = true;
        }
    }

}
