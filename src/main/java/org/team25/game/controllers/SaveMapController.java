package org.team25.game.controllers;

import org.team25.game.models.map.Continent;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;
import org.team25.game.utils.validation.MapValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SaveMapController {
    private GameMap d_gameMap;
    private String d_fileName;

    /**
     * Constructor to set the current GameMap object to read and show the map
     * @param p_gameMap GameMap Object Parameter
     * @param p_fileName Name with which file is going to be stored
     */
    public SaveMapController(GameMap p_gameMap, String p_fileName){
        this.d_gameMap = p_gameMap;
        this.d_fileName = p_fileName;
    }

    /**
     * Save Map function to save map
     * @return true if map got saved else false
     */
    public boolean saveMap(){
        if(!new MapValidator().ValidateMapObject(this.d_gameMap)){
            System.out.println("This Map Format is not valid");
            return false;
        }else{
            try {
                BufferedWriter l_writerPointer = new BufferedWriter(new FileWriter("src/main/resources/maps/"+this.d_fileName+".map"));
                int l_continent_idx = 1;
                int l_country_idx = 1;
                //These Hashmaps are for creating the border indexes
                HashMap<Integer, String> l_indexToCountry = new HashMap<Integer, String>();
                HashMap<String, Integer> l_countryToIndex = new HashMap<String, Integer>();

                //write basic information
                l_writerPointer.write("name " + this.d_fileName + " Map");
                l_writerPointer.newLine();
                l_writerPointer.newLine();
                l_writerPointer.write("[files]");
                l_writerPointer.newLine();
                l_writerPointer.newLine();
                l_writerPointer.flush();

                // Write Continents
                l_writerPointer.write("[continents]");
                l_writerPointer.newLine();
                for(Continent l_continent : this.d_gameMap.get_continents().values()) {
                    l_writerPointer.write(l_continent.get_continentId() + " " + l_continent.get_controlValue());
                    l_writerPointer.newLine();
                    l_writerPointer.flush();
                    l_continent.set_continentFileIndex(String.valueOf(l_continent_idx));
                    l_continent_idx++;
                }
                l_writerPointer.newLine();
                // Write Countries
                l_writerPointer.write("[countries]");
                l_writerPointer.newLine();
                for(Country l_country : this.d_gameMap.get_countries().values()) {
                    l_writerPointer.write(Integer.toString(l_country_idx) + " " + l_country.get_countryId() + " " + Integer.toString(this.d_gameMap.get_continents().get(l_country.get_parentContinent().toLowerCase()).get_continentFileIndex()) + " " + "0" + " " + "0");
                    l_writerPointer.newLine();
                    l_writerPointer.flush();
                    l_indexToCountry.put(l_country_idx, l_country.get_countryId().toLowerCase());
                    l_countryToIndex.put(l_country.get_countryId().toLowerCase(), l_country_idx);
                    l_country_idx++;
                }
                l_writerPointer.newLine();

                //Write Borders
                l_writerPointer.write("[borders]");
                l_writerPointer.newLine();
                l_writerPointer.flush();
                for(int i=1;i<l_country_idx;i++) {
                    String l_countryId = l_indexToCountry.get(i);
                    Country l_cd = this.d_gameMap.get_countries().get(l_countryId.toLowerCase());
                    l_writerPointer.write(Integer.toString(i) + " ");
                    for(Country l_neighbor : l_cd.get_Neighbours().values()) {
                        l_writerPointer.write(Integer.toString(l_countryToIndex.get(l_neighbor.get_countryId().toLowerCase())) + " ");
                        l_writerPointer.flush();
                    }
                    l_writerPointer.newLine();
                }
                l_writerPointer.close();
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

}
