package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TurnOrderCompiler {
    private static int HIGHEST_VALUE = 100;
    private Map<CharacterUnit, Integer> fieldedCharacters;
    private List<CharacterUnit> activeCharacters;


    public TurnOrderCompiler(List<CharacterUnit> playableCharacters, List<CharacterUnit> enemies) {
        fieldedCharacters = new LinkedHashMap<>();
        activeCharacters =  new ArrayList<>();
        insertListIntoActiveCharacters(playableCharacters);
        insertListIntoActiveCharacters(enemies);
    }

    private void insertListIntoActiveCharacters(List<CharacterUnit> characterUnits) {
        for (CharacterUnit unit : characterUnits) {
            fieldedCharacters.put(unit, HIGHEST_VALUE);
        }
    }

    public List<CharacterUnit> updateTurnOrder() {
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            Integer turnValue = entry.getValue();
            int newValue = updateCharactersPosition(unit, turnValue);
            if (isCharactersAction(newValue)) {
                newValue = addCharacterToActiveCharacters(unit);
            }
            entry.setValue(newValue); // put back in the new value
        }
        return activeCharacters;
    }

    private int updateCharactersPosition(CharacterUnit unit, Integer turnValue) {
        return turnValue - unit.getCharacterStatSheet().getSpeed();
    }

    private boolean isCharactersAction(Integer newValue) {
        return newValue <= 0;
    }

    private int addCharacterToActiveCharacters(CharacterUnit unit) {
        activeCharacters.add(unit);
        return HIGHEST_VALUE; // reset
    }

    public List<CharacterUnit> getAliveEnemyCharacters() {
        List<CharacterUnit> aliveEnemies = new ArrayList<>();
        for (CharacterUnit unit: activeCharacters) {
            if (unit.getClass() == NPC.class) {
                aliveEnemies.add(unit);
            }
        }
        return aliveEnemies;
    }
}
