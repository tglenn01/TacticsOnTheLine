package main.model.scenarioSystem;

import main.model.characterSystem.NPC;
import main.model.jobSystem.jobs.Archer;
import main.model.jobSystem.jobs.BattleMage;
import main.model.jobSystem.jobs.Cleric;
import main.model.jobSystem.jobs.Warrior;

public class ScenarioTwo extends Scenario {

    public ScenarioTwo() {
        super();
        scenarioName = "Woods";
    }

    protected void createListOfEnemies() {
        listOfEnemies.add(new NPC(new Warrior(), "Savage"));
        listOfEnemies.add(new NPC(new Cleric(), "Healer"));
        listOfEnemies.add(new NPC(new BattleMage(), "Caster"));
        listOfEnemies.add(new NPC(new Archer(), "Hunter"));
    }
}
