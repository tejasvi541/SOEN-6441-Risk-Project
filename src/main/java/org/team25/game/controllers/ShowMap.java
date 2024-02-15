package org.team25.game.controllers;

import org.team25.game.models.Continent;
import org.team25.game.models.Country;
import org.team25.game.models.GameMap;



public class ShowMap {

    private GameMap d_gameMap;

    /**
     * Constructor to set the current GMap object to read and show the map
     * @param p_gameMap GMap Object Parameter
     */
    public ShowMap(GameMap p_gameMap){
        this.d_gameMap = p_gameMap;
    }

    public void show(GameMap p_map) {
        if(p_map==null)
            return;
        System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
        System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
        System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
        boolean l_PrintContinentName = true;
        boolean l_PrintCountryName = true;
        for(Continent l_continent : p_map.get_continents().values()) {
            if(l_continent.get_countries().size()==0) {
                System.out.printf("\n%25s%25s%25s\n", l_continent.get_continentId(), "", "");
            }
            for(Country l_country : l_continent.get_countries().values()) {
                if(l_country.get_Neighbours().size()==0) {
                    if(l_PrintContinentName && l_PrintCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", l_continent.get_continentId(), l_country.get_countryId(), "");
                        l_PrintContinentName = false;
                        l_PrintCountryName = false;
                    }
                    else if(l_PrintCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", "", l_country.get_countryId(), "");
                        l_PrintCountryName =  false;
                    }
                }
                for(Country l_neighbor : l_country.get_Neighbours().values()) {
                    if(l_PrintContinentName && l_PrintCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", l_continent.get_continentId(), l_country.get_countryId(), l_neighbor.get_countryId());
                        l_PrintContinentName = false;
                        l_PrintCountryName = false;
                    }
                    else if(l_PrintCountryName) {
                        System.out.printf("\n%25s%25s%25s\n", "", l_country.get_countryId(), l_neighbor.get_countryId());
                        l_PrintCountryName = false;
                    }
                    else {
                        System.out.printf("%25s%25s%25s\n", "", "", l_neighbor.get_countryId());
                    }
                }
                l_PrintCountryName = true;
            }
            l_PrintContinentName = true;
            l_PrintCountryName = true;
        }
    }

}
