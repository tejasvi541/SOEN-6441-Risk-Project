package org.team21.game.controllers;

import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;
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
    /**
     * The d_GameMap is game map.
     */
    private GameMap d_GameMap;
    /**
     * The d_fileName is variable for file name.
     */
    private String d_FileName;

    /**
     * Constructor to set the current GameMap object to read and show the map
     * @param p_GameMap GameMap Object Parameter
     * @param p_FileName Name with which file is going to be stored
     */
    public SaveMapController(GameMap p_GameMap, String p_FileName){
        this.d_GameMap = p_GameMap;
        this.d_FileName = p_FileName;
    }

    /**
     * Save Map function to save map
     * @return true if map got saved else false
     */
    public boolean saveMap(){
        if(!new MapValidator().validateMapObject(this.d_GameMap)){
            System.out.println("This Map Format is not valid");
            return false;
        }else{
            try {
                BufferedWriter l_WriterPointer = new BufferedWriter(new FileWriter("src/main/resources/maps/"+this.d_FileName +".map"));
                int l_Continent_idx = 1;
                int l_Country_idx = 1;
                //These Hashmaps are for creating the border indexes
                HashMap<Integer, String> l_IndexToCountry = new HashMap<>();
                HashMap<String, Integer> l_CountryToIndex = new HashMap<>();

                //write basic information
                l_WriterPointer.write("name " + this.d_FileName + " Map");
                l_WriterPointer.newLine();
                l_WriterPointer.newLine();
                l_WriterPointer.write("[files]");
                l_WriterPointer.newLine();
                l_WriterPointer.newLine();
                l_WriterPointer.flush();

                // Write Continents
                l_WriterPointer.write("[continents]");
                l_WriterPointer.newLine();
                
                try {
                    for (Continent l_Continent : this.d_GameMap.getContinents().values()) {
                        l_WriterPointer.write(l_Continent.getContinentId() + " " + l_Continent.getControlValue());
                        l_WriterPointer.newLine();
                        l_WriterPointer.flush();
                        l_Continent.setContinentFileIndex(String.valueOf(l_Continent_idx));
                        l_Continent_idx++;
                    }
                    l_WriterPointer.newLine();
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                // Write Countries
                l_WriterPointer.write("[countries]");
                l_WriterPointer.newLine();
                try {
                    for (Country l_Country : this.d_GameMap.getCountries().values()) {
                        l_WriterPointer.write(l_Country_idx + " " + l_Country.getCountryId() + " " + this.d_GameMap.getContinents().get(l_Country.getParentContinent().toLowerCase()).getContinentFileIndex() + " " + "0" + " " + "0");
                        l_WriterPointer.newLine();
                        l_WriterPointer.flush();
                        l_IndexToCountry.put(l_Country_idx, l_Country.getCountryId().toLowerCase());
                        l_CountryToIndex.put(l_Country.getCountryId().toLowerCase(), l_Country_idx);
                        l_Country_idx++;
                    }
                    l_WriterPointer.newLine();
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //Write Borders
                l_WriterPointer.write("[borders]");
                l_WriterPointer.newLine();
                l_WriterPointer.flush();
                for(int i=1;i<l_Country_idx;i++) {
                    String l_CountryId = l_IndexToCountry.get(i);
                    try{
                        Country l_Cd = this.d_GameMap.getCountries().get(l_CountryId.toLowerCase());
                        l_WriterPointer.write(Integer.toString(i) + " ");
                        for (Country l_Neighbor : l_Cd.getNeighbours().values()) {
                            l_WriterPointer.write(Integer.toString(l_CountryToIndex.get(l_Neighbor.getCountryId().toLowerCase())) + " ");
                            l_WriterPointer.flush();
                        }
                        l_WriterPointer.newLine();
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                l_WriterPointer.close();
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
