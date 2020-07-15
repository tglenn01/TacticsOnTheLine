package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;

import java.util.List;

public class WeakpointAbility extends PhysicalAbility {
    public WeakpointAbility() {
        super("Weakpoint", 6, 1, 1,
                Ability.AbilityType.DAMAGE, 16, .80,
                "Strike an Enemies weakpoint with the blunt of your spear");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            resolveBattle(activeUnit, boardSpace.getOccupyingUnit());
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
        return false;
    }

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range);
    }

}
