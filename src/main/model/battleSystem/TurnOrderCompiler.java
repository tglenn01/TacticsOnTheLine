package main.model.battleSystem;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import main.exception.BattleIsOverException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TurnOrderCompiler {
    private static int HIGHEST_VALUE = 100;
    private static int SPEED_CONSTANT = 15;
    private Map<CharacterUnit, Integer> fieldedCharacters;

    public TurnOrderCompiler(List<CharacterUnit> playableCharacters, List<CharacterUnit> enemies) {
        fieldedCharacters = new LinkedHashMap<>();
        insertListIntoActiveCharacters(playableCharacters);
        insertListIntoActiveCharacters(enemies);
    }

    private void insertListIntoActiveCharacters(List<CharacterUnit> characterUnits) {
        for (CharacterUnit unit : characterUnits) {
            fieldedCharacters.put(unit, HIGHEST_VALUE);
        }
    }

    public List<CharacterUnit> updateTurnOrder() {
        List<CharacterUnit> charactersReadyToTakeAction = new ArrayList<>();
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
        return turnValue - (unit.getCharacterStatSheet().getSpeed() + SPEED_CONSTANT);
    }

    private boolean isCharactersAction(Integer newValue) {
        return newValue <= 0;
    }

    public List<CharacterUnit> getAliveEnemyCharacters() {
        List<CharacterUnit> aliveEnemies = new ArrayList<>();
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            if (unit.getClass() == NPC.class) {
                aliveEnemies.add(unit);
            }
        }
        return aliveEnemies;
    }

    public List<CharacterUnit> getAlivePlayableCharacters() {
        List<CharacterUnit> alivePlayableCharacters = new ArrayList<>();
        for (Map.Entry<CharacterUnit, Integer> entry : fieldedCharacters.entrySet()) {
            CharacterUnit unit = entry.getKey();
            if (TacticBaseBattle.getInstance().getPartyMemberList().contains(unit)) {
                alivePlayableCharacters.add(unit);
            }
        }
        return alivePlayableCharacters;
    }

    public List<CharacterUnit> getFieldedCharacters() {
        List<CharacterUnit> fieldedCharactersList = new ArrayList<>();
        for (Map.Entry<CharacterUnit, Integer> entry : this.fieldedCharacters.entrySet()) {
            fieldedCharactersList.add(entry.getKey());
        }
        return fieldedCharactersList;
    }

    public void removeDeadCharacterFromFieldedCharacters(CharacterUnit deadCharacter) throws BattleIsOverException {
        fieldedCharacters.remove(deadCharacter);
        if (deadCharacter.isMovementRangeIsVisable()) TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(deadCharacter);
        fadeDeadCharacter(deadCharacter);
        if (isBattleOver()) throw new BattleIsOverException();
    }

    public boolean isBattleOver() {
        return getAlivePlayableCharacters().isEmpty() || getAliveEnemyCharacters().isEmpty();
    }

    public boolean didUserWin() {
        return getAliveEnemyCharacters().isEmpty();
    }

    private void fadeDeadCharacter(CharacterUnit deadCharacter) {
        ImageView sprite = deadCharacter.getSpriteImageView();

        new AnimationTimer() {
            private double opacity = 1;
            private long delay = 1_000_000;
            private long prevTime = 0;

            @Override
            public void handle(long now) {

                if ((now - prevTime) >= delay) {
                    opacity -= 0.01;
                    sprite.opacityProperty().set(opacity);
                }

                prevTime = now;

                if (opacity <= 0) {
                    stop();
                    deadCharacter.getBoardSpace().removeOccupyingUnit();
                }
            }
        }.start();
    }
}
