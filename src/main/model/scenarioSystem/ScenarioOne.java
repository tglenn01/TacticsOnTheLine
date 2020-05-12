package main.model.scenarioSystem;

import main.model.characterSystem.NPC;
import main.model.jobSystem.jobs.Noble;

public class ScenarioOne extends Scenario {

    public ScenarioOne() {
        super();
    }

    protected void createListOfEnemies() {
        enemies.add(new NPC(new Noble(), "Dummy"));
    }
}
