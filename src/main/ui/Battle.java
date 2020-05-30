package main.ui;

import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private List<CharacterUnit> activeCharacters;
    private CharacterUnit activeCharacter;
    private int cursor;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        TacticBaseBattle.getInstance().setBattle(this);
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getListOfEnemies());
        updateNextRound();
    }

    private void updateNextRound() {
        this.activeCharacters = turnOrder.updateTurnOrder();
        if (activeCharacters.isEmpty()) updateNextRound();
        else {
            cursor = 0;
            startNewTurn(activeCharacters.get(0));
        }
    }

    private void startNewTurn(CharacterUnit activeCharacter) {
        if (activeCharacter.getIsAlive()) {
            this.activeCharacter = activeCharacter;
            activeCharacter.startTurn(this);
        }
    }

    public void endTurn() {
        cursor++;
        if (cursor == activeCharacters.size()) {
            activeCharacters.clear();
            updateNextRound();
        } else {
            startNewTurn(activeCharacters.get(cursor));
        }
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
