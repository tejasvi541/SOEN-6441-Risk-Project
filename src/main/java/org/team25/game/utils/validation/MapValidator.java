package org.team25.game.utils.validation;

import java.util.HashMap;
//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


import org.team25.game.models.map.Continent;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;



/**
 * This class uses an external library (JGraphT) to perform map validation tasks as presented in the Build handout
 * @author Tejasvi
 * @version 1.0.0
 */
public class MapValidator {

    private Graph<Country, DefaultEdge> d_gMapObj;


    /**
     * Initialisation Constructor for the graph
     */
    public MapValidator() {
        this.d_gMapObj = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
    private static final Logger d_Logger = LogManager.getLogger(MapValidator.class);



    public boolean ValidateMapObject(GameMap p_GameMap)
    {
        Graph<Country, DefaultEdge> l_graph=generate_Graph(p_GameMap);
        if (!isConnected(l_graph)) {
            d_Logger.info("Not a connected graph");
            return false;
        }
        if ( isContinentEmpty(p_GameMap)) {
            d_Logger.info("continent is empty");
            return false;
        }
        if (!isContinentConnected(p_GameMap)) {
            d_Logger.info("continent not connected");
            return false;
        }
        return true;
    }

    /**
     * This function takes a current GMap object and generate a JGraphT Graph for validation and other purposes
     * @param p_gameMap Current GMap Object
     * @return Generated Graph
     */
    public Graph<Country, DefaultEdge> generate_Graph(GameMap p_gameMap){

        // Add Vertex
        for(Country l_country : p_gameMap.get_countries().values()){
            this.d_gMapObj.addVertex(l_country);
        }
        // Add Neighbours
        for(Country l_country : p_gameMap.get_countries().values()){
            for(Country l_neighbour : l_country.get_Neighbours().values()){
                this.d_gMapObj.addEdge(l_country, l_neighbour);
            }
        }

        return this.d_gMapObj;
    }

    public Graph<Country, DefaultEdge> generate_SubGraph(Graph<Country, DefaultEdge> p_subGraph, HashMap<String, Country> p_countries){
        for(Country l_country : p_countries.values()){
            p_subGraph.addVertex(l_country);
        }

        for(Country l_country : p_countries.values()){
            for(Country l_neighbour : p_countries.values()){
                if(p_countries.containsKey(l_neighbour.get_countryId().toLowerCase())){
                    p_subGraph.addEdge(l_country, l_neighbour);
                }
            }
        }

        return p_subGraph;

    }

    /**
     * This function tells if the graph is connected or not
     * @param p_gGraph Map Graph Parameter to test if this map is connected or not
     * @return true/false if the map is connected or not
     */
    public boolean isConnected(Graph<Country, DefaultEdge> p_gGraph){
        ConnectivityInspector<Country, DefaultEdge> l_connectivityInspect = new ConnectivityInspector<>(p_gGraph);
        return l_connectivityInspect.isConnected();
    }

    /**
     * This function checks if all the continent subgraphs are connected or not using the isConnected Function
     * @param p_gameMap GMap Object to obtain the continent in the current Map
     * @return true/false if the continent subgraph is connected or not
     */
    public boolean isContinentConnected(GameMap p_gameMap){
        for(Continent l_continent : p_gameMap.get_continents().values()){
            Graph<Country, DefaultEdge> subGraph = generate_SubGraph(new DefaultDirectedGraph<>(DefaultEdge.class), l_continent.get_countries());
            if(!isConnected(subGraph)){
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks if the continent is present in the GMap Object
     * @param p_gameMap GMap object
     * @param p_continentId Id of the continent to be checked
     * @return true/false if the continent exists or not
     */
    public boolean isContinentPresent(GameMap p_gameMap, String p_continentId){
        return p_gameMap.get_continents().containsKey(p_continentId.toLowerCase());
    }

    /**
     * This function checks if the continent is present in the GMap Object
     * @param p_gameMap GMap object
     * @param p_countryId Id of the continent to be checked
     * @return true/false if the continent exists or not
     */
    public boolean isCountryPresent(GameMap p_gameMap, String p_countryId){
        return p_gameMap.get_countries().containsKey(p_countryId.toLowerCase());
    }

    /**
     * Checks if any continent is Empty
     * @param p_gameMap Current GMap Object
     * @return true/false if the continent is empty or not
     */
    public boolean isContinentEmpty(GameMap p_gameMap){
        for(Continent l_continent : p_gameMap.get_continents().values()){
            if(l_continent.get_countries().isEmpty()){
                return true;
            }
        }
        return false;
    }

}
