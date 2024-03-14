package org.team21.game.controllers;

import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.validation.MapValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * This Map Saves the Current Editted/Uneditted Game Map Object to a given file name under map folder
 * @author Tejasvi
 */
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
        if(!new MapValidator().validateMapObject(this.d_gameMap)){
            System.out.println("This Map Format is not valid");
            return false;
        }else{
            try {
                BufferedWriter l_writerPointer = new BufferedWriter(new FileWriter("src/main/resources/maps/"+this.d_fileName+".map"));
                int l_continent_idx = 1;
                int l_country_idx = 1;
                //These Hashmaps are for creating the border indexes
                HashMap<Integer, String> l_indexToCountry = new HashMap<>();
                HashMap<String, Integer> l_countryToIndex = new HashMap<>();

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
                
                try {
                    for (Continent l_continent : this.d_gameMap.getContinents().values()) {
                        l_writerPointer.write(l_continent.getContinentId() + " " + l_continent.getControlValue());
                        l_writerPointer.newLine();
                        l_writerPointer.flush();
                        l_continent.setContinentFileIndex(String.valueOf(l_continent_idx));
                        l_continent_idx++;
                    }
                    l_writerPointer.newLine();
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                // Write Countries
                l_writerPointer.write("[countries]");
                l_writerPointer.newLine();
                try {
                    for (Country l_country : this.d_gameMap.getCountries().values()) {
                        l_writerPointer.write(l_country_idx + " " + l_country.getCountryId() + " " + this.d_gameMap.getContinents().get(l_country.getParentContinent().toLowerCase()).getContinentFileIndex() + " " + "0" + " " + "0");
                        l_writerPointer.newLine();
                        l_writerPointer.flush();
                        l_indexToCountry.put(l_country_idx, l_country.getCountryId().toLowerCase());
                        l_countryToIndex.put(l_country.getCountryId().toLowerCase(), l_country_idx);
                        l_country_idx++;
                    }
                    l_writerPointer.newLine();
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //Write Borders
                l_writerPointer.write("[borders]");
                l_writerPointer.newLine();
                l_writerPointer.flush();
                for(int i=1;i<l_country_idx;i++) {
                    String l_countryId = l_indexToCountry.get(i);
                    try{
                        Country l_cd = this.d_gameMap.getCountries().get(l_countryId.toLowerCase());
                        l_writerPointer.write(Integer.toString(i) + " ");
                        for (Country l_neighbor : l_cd.getNeighbours().values()) {
                            l_writerPointer.write(Integer.toString(l_countryToIndex.get(l_neighbor.getCountryId().toLowerCase())) + " ");
                            l_writerPointer.flush();
                        }
                        l_writerPointer.newLine();
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
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
