package org.team25.Utils;

import java.util.HashMap;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.team25.Models.Continent;
import org.team25.Models.Country;
import org.team25.Models.GMap;



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
    MapValidator() {
        this.d_gMapObj = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * This function takes a current GMap object and generate a JGraphT Graph for validation and other purposes
     * @param p_gMap Current GMap Object
     * @return Generated Graph
     */
    public Graph<Country, DefaultEdge> generate_Graph(GMap p_gMap){

        // Add Vertex
        for(Country l_country : p_gMap.get_countries().values()){
            d_gMapObj.addVertex(l_country);
        }
        // Add Neighbours
        for(Country l_country : p_gMap.get_countries().values()){
            for(Country l_neighbour : l_country.get_Neighbours().values()){
                d_gMapObj.addEdge(l_country, l_neighbour);
            }
        }

        return d_gMapObj;
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
     * @param p_gMap GMap Object to obtain the continent in the current Map
     * @return true/false if the continent subgraph is connected or not
     */
    public boolean isContinentConnected(GMap p_gMap){
        for(Continent l_continent : p_gMap.get_continents().values()){
            Graph<Country, DefaultEdge> subGraph = generate_SubGraph(new DefaultDirectedGraph<>(DefaultEdge.class), l_continent.get_countries());
            if(!isConnected(subGraph)){
                return false;
            }
        }
        return true;
    }


}
