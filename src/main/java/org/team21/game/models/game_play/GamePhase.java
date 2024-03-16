package org.team21.game.models.game_play;

import org.team21.game.controllers.*;
import org.team21.game.interfaces.main_engine.GameFlowManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enum Game phase used by controllers and GamePhase
 * {ExecuteOrderController,ReinforcementController,MapEditorController,ReinforcementController,StartGameController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */

public enum GamePhase {

    /**
     * The Map editor.
     */
    MapEditor {
        /**
         * Retrieves the list of allowed next states from the MapEditor phase.
         *
         * @return A list containing the allowed next states from the MapEditor phase.
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(StartUp);
        }

        /**
         * Retrieves the controller for the map editor game phase.
         *
         * @return A MapEditor object serving as the controller for the map editor game phase.
         */
        @Override
        public GameFlowManager getController() {
            return new MapEditorController();
        }
    },
    /**
     * StartUp state handling load map, player creation and countries
     * allocation operations
     */
    StartUp {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from StartUp state
         *
         * @return List of allowed states from {@code StartUp phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(Reinforcement);
        }

        /**
         * Overrides getController() method which returns the controller
         * for game play or load game phase.
         *
         * @return GamePlay Object
         */
        @Override
        public GameFlowManager getController() {
            return new StartGameController();
        }
    },

    /**
     * Reinforcement state handles allocation of reinforcement armies
     * to each player after completing execute orders phase.
     */
    Reinforcement {
        /**
         * Retrieves the list of allowed next states from the Reinforcement phase.
         *
         * @return List of allowed states from the Reinforcement phase.
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(IssueOrder);
        }

        /**
         * Retrieves the controller for the reinforcement phase.
         *
         * @return Reinforcement Object.
         */
        @Override
        public GameFlowManager getController() {
            return new ReinforcementController();
        }
    },

    /**
     * IssueOrder state allows players to provide a list of orders.
     */
    IssueOrder {
        /**
         * Retrieves the list of allowed next states from the IssueOrder phase.
         *
         * @return List of allowed states from the IssueOrder phase.
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(ExecuteOrder);
        }

        /**
         * Retrieves the controller for the issue order phase.
         *
         * @return IssueOrder Object.
         */
        @Override
        public GameFlowManager getController() {
            return new IssueOrderController();
        }
    },

    /**
     * ExecuteOrder state allows the game engine to execute provided orders.
     */
    ExecuteOrder {
        /**
         * Retrieves the list of allowed next states from the ExecuteOrder phase.
         *
         * @return List of allowed states from the ExecuteOrder phase.
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Arrays.asList(Reinforcement, ExitGame);
        }

        /**
         * Retrieves the controller for the execute order phase.
         *
         * @return ExecuteOrder Object.
         */
        @Override
        public GameFlowManager getController() {
            return new ExecuteOrderController();
        }
    },

    /**
     * ExitGame state ends the game once all countries are conquered.
     */
    ExitGame {
        /**
         * Retrieves the list of allowed next states from the ExitGame phase.
         *
         * @return List of allowed states from the ExitGame phase.
         */
        @Override
        public List<GamePhase> possibleStates() {
            return null;
        }

        /**
         * Retrieves the controller for the exit game phase.
         *
         * @return null.
         */
        @Override
        public GameFlowManager getController() {
            return null;
        }
    };

    /**
     * Commands will help for assisting in test cases
     */
    public String Commands;

    /**
     * Checks if the next game phase returned from the current
     * controller is present in its possible states list.
     *
     * @param p_NextPhase Next phase of the game.
     * @return Next game phase if valid; otherwise, returns the current game phase.
     */
    public GamePhase nextState(GamePhase p_NextPhase) {
        if (this.possibleStates().contains(p_NextPhase)) {
            return p_NextPhase;
        } else {
            return this;
        }
    }

    /**
     * Possible states list.
     *
     * @return the list
     */
    public abstract List<GamePhase> possibleStates();

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public abstract GameFlowManager getController();
}
