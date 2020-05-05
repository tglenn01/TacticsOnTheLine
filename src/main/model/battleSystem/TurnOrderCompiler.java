package main.model.battleSystem;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.characterSystem.PlayableCharacterUnit;

import java.util.List;
import java.util.Map;

public class TurnOrderCompiler {
    private static  TurnOrderCompiler turnOrderCompiler;
    private Map<CharacterUnit, Integer> activeCharacters;


    private TurnOrderCompiler() {

    }

    public static TurnOrderCompiler getInstance() {
        if (turnOrderCompiler == null) {
            return new TurnOrderCompiler();
        }
        return turnOrderCompiler;
    }

    public void setCharactersInBattleToTurnOrder(List<PlayableCharacterUnit> playableCharacters, List<NPC> enemies) {

    }

}
