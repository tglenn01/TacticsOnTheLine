package main.model.battleSystem;

import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;

import java.util.List;

public class Battle {
    private TurnOrderCompiler turnOrder;

    public Battle(List<CharacterUnit> playableCharacters, Scenario scenario) {
        turnOrder = new TurnOrderCompiler(playableCharacters, scenario.getEnemies());
    }
}
