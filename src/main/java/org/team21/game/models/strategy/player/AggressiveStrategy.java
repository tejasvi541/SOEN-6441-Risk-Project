package org.team21.game.models.strategy.player;

import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.order.*;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implements the Aggressive strategy for a player.
 * This strategy focuses on deploying armies to the strongest country, attacking enemies, and bombing if a bomb card is available.
 *
 * @author Meet Boghani
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {
    /**
     * The logger instance for recording game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * A list of countries sorted based on the number of armies.
     */
    private List<Country> orderedList;

    /**
     * Creates commands for deploying, advancing, and bombing for an Aggressive player.
     *
     * @return "pass" if no valid actions can be performed.
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Aggressive Player - " + d_Player.getName());
        if (d_Player.getCapturedCountries().size() > 0) {
            createAndOrderCountryList();
            deployCommand();
            if (bombOrAttack()) {
                return "pass";
            }
            moveToSelf();
        }
        return "pass";
    }

    /**
     * Creates a list of countries sorted by their army strength.
     */
    private void createAndOrderCountryList() {
        orderedList = d_Player.getCapturedCountries()
                .stream()
                .sorted(Comparator.comparingInt(Country::getArmies).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Generates a deploy command to strengthen the strongest country.
     *
     * @return true if the command is successfully executed, false otherwise.
     */
    public boolean deployCommand() {
        List<String> l_Commands = new ArrayList<>();
        Country l_StrongCountry = orderedList.get(0);
        int l_armiesReinforce = d_Player.getReinforcementArmies();
        // Deploy armies to strongestCountry
        l_Commands.add(0, Constants.DEPLOY_COMMAND);
        l_Commands.add(1, l_StrongCountry.getName());
        l_Commands.add(2, String.valueOf((l_armiesReinforce)));
        String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
        Order l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderOwner.generateDeployOrderInfo(l_CommandsArr, d_Player));
        IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
        d_Player.issueOrder();
        return true;
    }

    /**
     * Bombs an enemy if a bomb card exists; otherwise, attacks the enemy with the strongest country.
     *
     * @return true if the command is successfully executed, false otherwise.
     */
    public boolean bombOrAttack() {
        boolean flag = false;
        for (Card playerCard : d_Player.getPlayerCards()) {
            if (playerCard.getCardType().equals(CardType.BOMB)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            Country l_EnemyWithHighTroops = d_Player.getCapturedCountries().stream()
                    .flatMap(country -> country.getNeighbors().stream())
                    .filter(country -> !d_Player.getName().equals(country.getPlayer().getName()))
                    .max(Comparator.comparingInt(Country::getArmies))
                    .orElse(null);
            if (Objects.nonNull(l_EnemyWithHighTroops)) {
                List<String> l_Commands = new ArrayList<>();
                l_Commands.add(0, Constants.BOMB_COMMAND);
                l_Commands.add(1, l_EnemyWithHighTroops.getName());
                String[] l_CommandsArr = l_Commands.toArray(new String[0]);
                Order l_Order = new BombOrder();
                l_Order.setOrderInfo(OrderOwner.generateBombOrderInfo(l_CommandsArr, d_Player));
                IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
                d_Player.issueOrder();
                return true;
            }
        }
        flag = false;
        Country l_FromCountry = null;
        Country l_ToCountry = null;
        for (Country l_Country : orderedList) {
            l_FromCountry = l_Country;
            Country l_EnemyCountry = l_Country.getNeighbors()
                    .stream()
                    .filter(country -> !d_Player.getName().equals(country.getPlayer().getName()))
                    .findFirst().orElse(null);
            l_ToCountry = l_EnemyCountry;
            if (Objects.nonNull(l_EnemyCountry) && l_FromCountry.getArmies() > 0) {
                List<String> l_Commands = new ArrayList<>();
                l_Commands.add(0, Constants.ADVANCE_COMMAND);
                l_Commands.add(1, l_FromCountry.getName());
                l_Commands.add(2, l_ToCountry.getName());
                l_Commands.add(3, String.valueOf(l_FromCountry.getArmies()));
                String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                Order l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderOwner.generateAdvanceOrderAndAirliftOrderInfo(l_CommandsArr, d_Player));
                IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
                d_Player.issueOrder();
                flag = true;
            }
        }


        return flag;
    }


    /**
     * If no enemies exist near the strongest country, moves armies to the next strongest country that has an enemy.
     *
     * @return true if the command is successfully executed, false otherwise.
     */
    private boolean moveToSelf() {
        Country fromCountry = orderedList.get(0);
        if (fromCountry.getArmies() <= 0) {
            return false;
        }
        List<Country> l_NeighborsWithEnemies = getNeighborsWithEnemies(fromCountry);
        Country toCountry = l_NeighborsWithEnemies.stream().max(Comparator.comparingInt(Country::getArmies)).orElse(null);
        if (Objects.nonNull(fromCountry) && Objects.nonNull(toCountry)) {
            List<String> l_Commands = new ArrayList<>();
            l_Commands.add(0, Constants.ADVANCE_COMMAND);
            l_Commands.add(1, fromCountry.getName());
            l_Commands.add(2, toCountry.getName());
            l_Commands.add(3, String.valueOf(fromCountry.getArmies()));
            String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
            Order l_Order = new AdvanceOrder();
            l_Order.setOrderInfo(OrderOwner.generateAdvanceOrderAndAirliftOrderInfo(l_CommandsArr, d_Player));
            IssueOrderController.d_Commands = l_Order.getOrderInfo().getCommand();
            d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrderController.d_Commands));
            d_Player.issueOrder();
            return true;
        }
        return false;
    }

    /**
     * Retrieves neighbors of a country with enemies.
     *
     * @param p_FromCountry The country to check for neighbors with enemies.
     * @return List of countries neighboring the given country that have enemies.
     */
    private List<Country> getNeighborsWithEnemies(Country p_FromCountry) {
        return p_FromCountry.getNeighbors().stream()
                .takeWhile(country -> {
                    Long count = country.getNeighbors().stream()
                            .filter(country1 -> !country.getPlayer().getName().equals(country1.getPlayer().getName()))
                            .count();
                    if (count > 0) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
    }

}
