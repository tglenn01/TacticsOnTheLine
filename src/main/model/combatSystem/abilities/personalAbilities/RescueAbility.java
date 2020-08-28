package main.model.combatSystem.abilities.personalAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

public class RescueAbility extends Ability {
    private CharacterUnit unitWithThisAbility;

    public RescueAbility(CharacterUnit unitWithThisAbility) {
        super("Rescue", 10, 1, 1,
                "Teleport a far away ally to your side");
        this.unitWithThisAbility = unitWithThisAbility;
    }

    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        BoardSpace closetBoardSpace = TacticBaseBattle.getInstance().getCurrentBoard().getClosetBoardSpace(activeUnit.getBoardSpace());
        receivingUnit.setBoardSpace(closetBoardSpace);
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }


    @Override
    public int getRange() {
        return unitWithThisAbility.getCharacterStatSheet().getMagic();
    }
}
