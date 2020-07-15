package main.model.jobSystem;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.List;

public class DefendAbility extends StatusEffectAbility {
    public DefendAbility() {
        super("Defend", 0, 0, 1,
                1, Ability.AbilityType.DEFENSE_BUFF, 3, "Strengthen one's own defenses");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            resolveEffect(activeUnit, boardSpace.getOccupyingUnit());
        }
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
        return true;
    }

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getSelfBoardSpace(activeUnit);
    }

}
