package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

import java.util.List;

public class RescueAbility extends Ability {
    private CharacterUnit unit;

    public RescueAbility(CharacterUnit unit) {
        super("Rescue", 10, 1, 1,
                Ability.AbilityType.MOVEMENT, "Teleport a far away ally to your side");
        this.unit = unit;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) throws AttackMissedException, UnitIsDeadException {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
            BoardSpace closetBoardSpace = TacticBaseBattle.getInstance().getCurrentBoard().getClosetBoardSpace(activeUnit.getBoardSpace());
            receivingUnit.setBoardSpace(closetBoardSpace);
        }

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
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), activeUnit.getCharacterStatSheet().getMagic()); // their magic stat is their range
    }
}
