package main.ui;

import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;

import java.util.Iterator;
import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private Scenario scenario;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        this.scenario = scenario;
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getEnemies());
        try {
            updateNextRound();
        } catch (BattleIsOverException e) {
            System.out.println("The Battle is over");
        }
    }

    private void updateNextRound() throws BattleIsOverException {
        List<CharacterUnit> activeCharacters = turnOrder.updateTurnOrder();
        takeAction(activeCharacters);
        turnOrder.removeAllDeadCharacters();
        updateNextRound();
    }

    private void takeAction(List<CharacterUnit> activeCharacters) throws BattleIsOverException {
        for (Iterator<CharacterUnit> iterator = activeCharacters.iterator(); iterator.hasNext();) {
            CharacterUnit activeCharacter = iterator.next();
            if (activeCharacter.getIsAlive()) { // If unit is Alive it takes an action
                activeCharacter.takeAction(this);
                iterator.remove();
            }
            if (turnOrder.isBattleOver()) { // If all of one side is dead end the loop
                throw new BattleIsOverException();
            }
        }

    }

    public TurnOrderCompiler getTurnOrder() {
        return turnOrder;
    }

    public void removeDeadCharacter(CharacterUnit deadCharacter) {
        turnOrder.addDeadCharacterToListOfDeadCharacters(deadCharacter);
    }
}
