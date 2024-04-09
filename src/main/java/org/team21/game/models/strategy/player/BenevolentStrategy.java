package org.team21.game.models.strategy.player;

import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.models.order.*;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Implements the Benevolent player strategy, focusing on strengthening the weakest country and avoiding conflicts.
 * This strategy is characterized by deploying armies to the weakest country, negotiating with random players if diplomacy cards are available,
 * and reinforcing the weakest country further by moving armies from neighboring friendly countries.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class BenevolentStrategy extends PlayerStrategy implements Serializable {

    /**
     * Random number generator
     */
    private static final Random d_Random = new Random();

    /**
     * Logger for game events
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Instance of the game map
     */
    private static GameMap d_GameMap;

    /**
     * Retrieves the weakest conquered country from the player's captured countries.
     *
     * @param p_Player The player object for which to find the weakest conquered country.
     * @return The weakest conquered country owned by the player, or null if no countries are captured.
     */
    public Country getWeakestConqueredCountry(Player p_Player) {
        List<Country> l_CountryList = p_Player.getCapturedCountries();
        if (l_CountryList.size() > 0) {
            Country l_WeakestCountry = l_CountryList.get(0);
            for (Country l_Country : l_CountryList) {
                if (l_Country.getArmies() < l_WeakestCountry.getArmies())
                    l_WeakestCountry = l_Country;
            }
            return l_WeakestCountry;
        }
        return null;
    }

    /**
     * Creates commands for deploying, negotiating, and advancing for a Benevolent player.
     *
     * @return "pass" if no action is taken; otherwise, returns the result of the action.
     */
    public String createCommand() {
        d_GameMap = GameMap.getInstance();
        d_Player = d_GameMap.getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Benevolent Player - " + d_Player.getName());
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_WeakestCountry = getWeakestConqueredCountry(d_Player);
        if (Objects.isNull(l_WeakestCountry)) {
            return "pass";
        }
        int l_ArmiesReinforce = d_Player.getReinforcementArmies();

        // Deploy armies to weakest Country
        l_Commands.add(0, Constants.DEPLOY_COMMAND);
        l_Commands.add(1, l_WeakestCountry.getName());
        l_Commands.add(2, String.valueOf((l_ArmiesReinforce)));
        l_CommandsArr = l_Commands.toArray(new String[0]);
        l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderOwner.generateDeployOrderInfo(l_CommandsArr, d_Player));
        IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
        d_Player.issueOrder();

        //if Player has a diplomacy card,then use it
        if (d_Player.getPlayerCards().size() > 0) {
            for (Card l_Card : d_Player.getPlayerCards()) {
                if (l_Card.getCardType() == CardType.DIPLOMACY) {
                    Player l_RandomPlayer = getRandomPlayer(d_Player);
                    if (Objects.nonNull(l_RandomPlayer)) {
                        l_Commands = new ArrayList<>();
                        l_Commands.add(0, Constants.NEGOTIATE_COMMAND);
                        l_Commands.add(1, l_RandomPlayer.getName());
                        l_CommandsArr = l_Commands.toArray(new String[0]);
                        l_Order = new NegotiateOrder();
                        l_Order.setOrderInfo(OrderOwner.generateNegotiateOrderInfo(l_CommandsArr, d_Player));
                        IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
                        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
                        d_Player.issueOrder();
                        return "pass";
                    }
                }
            }
        }

// move armies to the weakest country from the other neighbouring countries of the same player
        for (Country l_Country : l_WeakestCountry.getNeighbors()) {
            if (l_Country.getPlayer().getName().equals(d_Player.getName())) {
                l_Commands = new ArrayList<>();
                l_Commands.add(0, Constants.ADVANCE_COMMAND);
                l_Commands.add(1, l_Country.getName());
                l_Commands.add(2, l_WeakestCountry.getName());
                l_Commands.add(3, String.valueOf(l_Country.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[0]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderOwner.generateAdvanceOrderAndAirliftOrderInfo(l_CommandsArr, d_Player));
                IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
                d_Player.issueOrder();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
                l_WeakestCountry = l_Country;
            }
        }
        return "pass";
    }

    /**
     * Gets a random player other than the current player.
     *
     * @param d_Player The current player.
     * @return A random player other than the current player, or null if no such player exists.
     */
    protected Player getRandomPlayer(Player d_Player) {
        List<Country> l_Enemies = d_Player.getCapturedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !country.getPlayer().getName().equals(d_Player.getName()))
                .collect(Collectors.toList());
        if (l_Enemies.size() > 0) {
            int l_Random = d_Random.nextInt(l_Enemies.size());
            return l_Enemies.get(l_Random).getPlayer();
        }
        return null;
    }
}
