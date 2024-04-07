package org.team21.game.models.tournament;

/**
 * Class to implement the tournament result
 *
 * @author Nishith Soni
 */
public class TournamentResult {
    /**
     * private data member to hold map
     */
    private String d_Map;
    /**
     * data member to hold number of games
     */
    private int d_Game;
    /**
     * data member to hold winner of the game
     */
    private String d_Winner;

    /**
     * get the map
     *
     * @return the map
     */
    public String getMap() {
        return d_Map;
    }

    /**
     * set the map
     *
     * @param p_Map hold the map
     */
    public void setMap(String p_Map) {
        d_Map = p_Map;
    }
    /**
     * get the game
     *
     * @return the game
     */
    public int getGame() {
        return d_Game;
    }
    /**
     * set the game
     *
     * @param p_Game hold the game
     */
    public void setGame(int p_Game) {
        d_Game = p_Game;
    }
    /**
     * To get winner of the game
     *
     * @return winner of the game
     */
    public String getWinner() {
        return d_Winner;
    }
    /**
     * set winner of the game
     *
     * @param p_Winner hold the winner
     */
    public void setWinner(String p_Winner) {
        d_Winner = p_Winner;
    }
}
