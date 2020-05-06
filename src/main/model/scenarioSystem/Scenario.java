package main.model.scenarioSystem;

import main.model.characterSystem.CharacterUnit;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    protected List<CharacterUnit> enemies;

    public Scenario() {
        enemies = new ArrayList<>();
        createListOfEnemies();
    }

    protected abstract void createListOfEnemies();

    public List<CharacterUnit> getEnemies() {
        return enemies;
    }
}
