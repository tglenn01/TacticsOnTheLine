package main.ui;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.PlayableCharacterUnit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TurnOrderCompiler {
    private static int HIGHEST_VALUE = 100;
    private Map<CharacterUnit, Integer> fieldedCharacters;
    private List<CharacterUnit> charactersReadyToTakeAction;
    private List<CharacterUnit> charactersThatDiedThisRound;

    public TurnOrderCompiler(List<CharacterUnit> playableCharacters, List<CharacterUnit> enemies) {
        fieldedCharacters = new LinkedHashMap<>();
        charactersReadyToTakeAction = new ArrayList<>();
        charactersThatDiedThisRound = new ArrayList<>();
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
                charactersReadyToTakeAction.add(unit);
                newValue = HIGHEST_VALUE;
            }
            entry.setValue(newValue); // put back in the new value
        }
        return charactersReadyToTakeAction;
    }

    private int updateCharactersPosition(CharacterUnit unit, Integer turnValue) {
        return turnValue - unit.getCharacterStatSheet().getSpeed();
    }

    private boolean isCharactersAction(Integer newValue) {
        return newValue <= 0;
    }

    public List<CharacterUnit> getAliveEnemyCharacters() {
        List<CharacterUnit> aliveEnemies = new ArrayList<>();
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            if (unit.getClass() == NPC.class && !charactersThatDiedThisRound.contains(unit)) {
                aliveEnemies.add(unit);
            }
        }
        return aliveEnemies;
    }

    public List<CharacterUnit> getAlivePlayableCharacters() {
        List<CharacterUnit> alivePlayableCharacters = new ArrayList<>();
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            if (unit.getClass() == PlayableCharacterUnit.class) {
                alivePlayableCharacters.add(unit);
            }
        }
        return alivePlayableCharacters;
    }

    public List<CharacterUnit> getCharactersReadyToTakeAction() {
        return charactersReadyToTakeAction;
    }

    public void addDeadCharacterToListOfDeadCharacters(CharacterUnit deadCharacter) {
        charactersThatDiedThisRound.add(deadCharacter);
    }

    public boolean isBattleOver() {
        return getAlivePlayableCharacters().isEmpty() || getAliveEnemyCharacters().isEmpty();
    }

    public void removeAllDeadCharacters() {
        for (CharacterUnit unit : charactersThatDiedThisRound) {
            fieldedCharacters.remove(unit);
        }
        charactersThatDiedThisRound = new ArrayList<>();
    }
}
