package main.model.combatSystem.abilities.personalAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.SupportiveAbility;

import java.util.List;

public class DeactivateAbility extends SupportiveAbility {

    public DeactivateAbility() {
        super("Deactivate", 0, 0, 1,
                "Pass your turn to heal to full");
    }

    @Override
    // receivingUnit and activeUnit are the same in this ability
    public void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        healUnit(receivingUnit, receivingUnit.getCharacterStatSheet(), activeUnit.getCharacterStatSheet().getMaxHealth());

        activeUnit.setMovementToken(false);
        activeUnit.setActionToken(0);
    }


    @Override
    protected boolean targetsSelf() {
        return true;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getSelfBoardSpace(activeUnit);
    }
}
