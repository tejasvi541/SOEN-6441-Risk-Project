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
        if (d_gameMap.getMapName() == null || d_gameMap.getContinents() == null || d_gameMap.getMapName() == "" || d_gameMap == null){
            System.out.println("Any Map is not Loaded");
            return;
        }
        System.out.printf("%100s\n", "===============================================================================================================================");
        System.out.printf("%-30s%-30s%-10s%-10s%-50s%n\n", "Continents", "Country", "Players",  "Armies", "Country's Neighbors");
        System.out.printf("%100s\n", "===============================================================================================================================");
        boolean l_isContinentPrinting = true;
        boolean l_isCountryPrinting = true;
        for(Continent l_continent : d_gameMap.getContinents().values()) {
            if(l_continent.getCountries().isEmpty()) {
                System.out.printf("\n%-30s%-30s%-10s%-10s%-50s%n\n", l_continent.getContinentId(), "", "", "", "");
            }
            for(Country l_country : l_continent.getCountries().values()) {
                if(l_country.get_Neighbours().isEmpty()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%-30s%-30s%-10s%-10s%-50s%n\n", l_continent.getContinentId(), l_country.get_countryId(), l_country.getPlayer().getName(), l_country.getArmies(), "");
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%-30s%-30s%-10s%-10s%-50s%n\n", "", l_country.get_countryId(), l_country.getPlayer().getName(), l_country.getArmies(), "");
                        l_isCountryPrinting =  false;
                    }
                }
                for(Country l_neighbor : l_country.get_Neighbours().values()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%-30s%-30s%-10s%-10s%-50s%n\n", l_continent.getContinentId(), l_country.get_countryId(), l_country.getPlayer().getName(), l_country.getArmies(), l_neighbor.get_countryId());
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%-30s%-30s%-10s%-10s%-50s%n\n", "", l_country.get_countryId(), l_country.getPlayer().getName(), l_country.getArmies(), l_neighbor.get_countryId());
                        l_isCountryPrinting = false;
                    }
                    else {
                        System.out.printf("%-30s%-30s%-10s%-10s%-50s%n\n", "", "", "", "", l_neighbor.get_countryId());
                    }
                }
                l_isCountryPrinting = true;
            }
            l_isContinentPrinting = true;
            l_isCountryPrinting = true;
        }
    }

}
