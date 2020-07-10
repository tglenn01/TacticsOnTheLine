package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

public class RescueAbility extends Ability {
    CharacterUnit unit;

    public RescueAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, String abilityDescription, CharacterUnit unit) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
        this.unit = unit;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        BoardSpace closetBoardSpace = TacticBaseBattle.getInstance().getCurrentBoard().getClosetBoardSpace(activeUnit.getBoardSpace());
        receivingUnit.setBoardSpace(closetBoardSpace);
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }

    @Override
    public int getRange() {
        return unit.getCharacterStatSheet().getMagic();
    }
}
