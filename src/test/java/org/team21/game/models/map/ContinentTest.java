package org.team21.game.models.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class ContinentTest {
    private Continent d_Continent;
    @Before
    public void setUp(){
        d_Continent = new Continent();
        d_Continent.setId("Asia");
        d_Continent.setName("Asia");
        d_Continent.setCredited(true);
        d_Continent.setAwardArmies(10);
        d_Continent.setCountries(new HashSet<>());
    }
    @Test
    public void getId() {
        assertEquals(d_Continent.getId(), "Asia");
    }

    @Test
    public void getName() {
        assertEquals(d_Continent.getName(), "Asia");
    }


    @Test
    public void getAwardArmies() {
        assertEquals(d_Continent.getAwardArmies(), 10);
    }

    @Test
    public void isCredited() {
        assertEquals(d_Continent.isCredited(), true);
    }

    @Test
    public void getCountries() {
        assertEquals(d_Continent.getCountries(), new HashSet<>());
    }
}