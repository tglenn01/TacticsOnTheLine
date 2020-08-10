package main.model.combatSystem.abilities.personalAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

import java.util.List;

public class RescueAbility extends Ability {
    private CharacterUnit unit;

    public RescueAbility(CharacterUnit unit) {
        super("Rescue", 10, 1, 1,
                "Teleport a far away ally to your side");
        this.unit = unit;
    }

    @Override
    public void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        BoardSpace closetBoardSpace = TacticBaseBattle.getInstance().getCurrentBoard().getClosetBoardSpace(activeUnit.getBoardSpace());
        receivingUnit.setBoardSpace(closetBoardSpace);
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
    public List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), activeUnit.getCharacterStatSheet().getMagic(), this); // their magic stat is their range
    }
}
