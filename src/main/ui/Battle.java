package main.ui;

import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private List<CharacterUnit> activeCharacters;
    private CharacterUnit activeCharacter;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getListOfEnemies());
        try {
            updateNextRound();
        } catch (BattleIsOverException e) {
            System.out.println("The Battle is over");
            if (turnOrder.didUserWin()) {
                System.out.println("You have won");
            } else System.out.println("You have lost");
        }
    }

    private void updateNextRound() throws BattleIsOverException {
        this.activeCharacters = turnOrder.updateTurnOrder();
        if (!activeCharacters.isEmpty()) startNewTurn(activeCharacters.iterator().next());
        updateNextRound();
    }

    private void startNewTurn(CharacterUnit activeCharacter) throws BattleIsOverException {
        TacticBaseBattle.getInstance().getCurrentBoard().setActiveCharacter(activeCharacter);
        this.activeCharacter = activeCharacter;
        activeCharacter.startTurn(this);
        //activeCharacters.iterator().remove();
    }

    public void endTurn() throws BattleIsOverException {
        if (activeCharacters.iterator().hasNext()) {
            startNewTurn(activeCharacters.iterator().next());
        } else updateNextRound();
    }

    public TurnOrderCompiler getTurnOrder() {
        return turnOrder;
    }

    public CharacterUnit getActiveCharacter() { return activeCharacter; }

    public void removeDeadCharacter(CharacterUnit deadCharacter) {
        try{
            turnOrder.removeDeadCharacterFromFieldedCharacters(deadCharacter);
        } catch (BattleIsOverException e) {
            //if (turnOrder.didUserWin()) new VictoryScreen();
            //else new DefeatScreen();
        }
    }
}
