package main.model.jobSystem.jobs.archerJob.archerAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;

import java.util.List;

public class SnipeAbility extends PhysicalAbility {
    public SnipeAbility() {
        super("Snipe", 2, 6, 1,
                Ability.AbilityType.DAMAGE,8, .95,
                "Accurate attack from afar to damage a single foe");
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
