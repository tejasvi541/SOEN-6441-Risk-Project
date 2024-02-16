package org.team25.game.controllers;

import org.team25.game.models.map.Continent;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;


/**
 * This is the Controller to show the full Map in the terminal
 * @author Tejasvi
 */
public class ShowMapController {

    private GameMap d_gameMap;

    /**
     * Constructor to set the current GameMap object to read and show the map
     * @param p_gameMap GMap Object Parameter
     */
    public ShowMapController(GameMap p_gameMap){
        this.d_gameMap = p_gameMap;
    }

    public void show() {
        if(d_gameMap==null)
            return;
        System.out.printf("%100s\n", "===============================================================================================================================");
        System.out.printf("%30s%30s%60s\n", "Continents", "Country", "Country's Neighbors");
        System.out.printf("%100s\n", "===============================================================================================================================");
        boolean l_isContinentPrinting = true;
        boolean l_isCountryPrinting = true;
        for(Continent l_continent : p_gameMap.get_continents().values()) {
            if(l_continent.get_countries().isEmpty()) {
                System.out.printf("\n%25s%25s%25s\n", l_continent.get_continentId(), "", "");
            }
            for(Country l_country : l_continent.get_countries().values()) {
                if(l_country.get_Neighbours().isEmpty()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", l_continent.get_continentId(), l_country.get_countryId(), "");
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", "", l_country.get_countryId(), "");
                        l_isCountryPrinting =  false;
                    }
                }
                for(Country l_neighbor : l_country.get_Neighbours().values()) {
                    if(l_isContinentPrinting && l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", l_continent.get_continentId(), l_country.get_countryId(), l_neighbor.get_countryId());
                        l_isContinentPrinting = false;
                        l_isCountryPrinting = false;
                    }
                    else if(l_isCountryPrinting) {
                        System.out.printf("\n%35s%35s%50s\n", "", l_country.get_countryId(), l_neighbor.get_countryId());
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
