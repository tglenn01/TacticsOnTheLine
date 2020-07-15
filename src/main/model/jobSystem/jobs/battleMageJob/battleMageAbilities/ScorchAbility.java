package main.model.jobSystem.jobs.battleMageJob.battleMageAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;

import java.util.List;

public class ScorchAbility extends MagicAbility {
    public ScorchAbility() {
        super("Scorch", 6, 3, 2,
                Ability.AbilityType.DAMAGE,10, .70,
                "deal light fire damage to multiple enemies from afar");
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
        return true;
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
