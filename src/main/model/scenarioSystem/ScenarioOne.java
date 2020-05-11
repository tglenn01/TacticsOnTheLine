package main.model.scenarioSystem;

import main.model.characterSystem.NPC;

public class ScenarioOne extends Scenario {

    public ScenarioOne() {
        super();
    }

    protected void createListOfEnemies() {
        enemies.add(new NPC("Dummy"));
    }
}
