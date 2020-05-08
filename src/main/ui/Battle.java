package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;
    private Scenario scenario;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        this.scenario = scenario;
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getEnemies());
        startBattle();
    }

    private void startBattle() {
        List<CharacterUnit> activeCharacters = turnOrder.updateTurnOrder();
        for (CharacterUnit unit : activeCharacters) {
            unit.takeAction(this);
        }
    }

    public TurnOrderCompiler getTurnOrder() {
        return turnOrder;
    }
}
