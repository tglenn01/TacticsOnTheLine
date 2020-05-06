package main.model.battleSystem;

import main.model.characterSystem.CharacterUnit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TurnOrderCompiler {
    private static int HIGHEST_VALUE = 100;
    private Map<CharacterUnit, Integer> fieldedCharacters;
    private List<CharacterUnit> activeCharacter;


    public TurnOrderCompiler() {
        fieldedCharacters = new LinkedHashMap<>();
        activeCharacter =  new ArrayList<>();
    }

    public void setCharactersInBattleToTurnOrder(List<CharacterUnit> playableCharacters, List<CharacterUnit> enemies) {
        insertListIntoActiveCharacters(playableCharacters);
        insertListIntoActiveCharacters(enemies);
    }

    private void insertListIntoActiveCharacters(List<CharacterUnit> characterUnits) {
        for (CharacterUnit unit : characterUnits) {
            fieldedCharacters.put(unit, HIGHEST_VALUE);
        }
    }

    private void updateTurnOrder() {
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            Integer turnValue = entry.getValue();
            int newValue = updateCharactersPosition(unit, turnValue);
            if (isCharactersAction(newValue)) {
                newValue = addCharacterToActiveCharacters(unit);
            }
            entry.setValue(newValue); // put back in the new value
        }
    }

    private int updateCharactersPosition(CharacterUnit unit, Integer turnValue) {
        return turnValue - unit.getCharacterStatSheet().getSpeed();
    }

    private boolean isCharactersAction(Integer newValue) {
        return newValue <= 0;
    }

    private int addCharacterToActiveCharacters(CharacterUnit unit) {
        activeCharacter.add(unit);
        return HIGHEST_VALUE; // reset
    }
}
