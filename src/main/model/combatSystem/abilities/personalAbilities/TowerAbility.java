package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.statusEffects.IncreasedRange;
import main.model.combatSystem.statusEffects.Root;

import java.util.List;

public class TowerAbility extends Ability {

    public TowerAbility() {
        super("Tower", 20, 0, 1, null,
                "Root yourself increasing the range of magical abilities");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) throws AttackMissedException, UnitIsDeadException {
        DecayingStatusEffect root = new Root(activeUnit, activeUnit.getCharacterStatSheet().getMovement(), 3);
        DecayingStatusEffect doubleRange = new IncreasedRange(activeUnit, 4, 3);
        activeUnit.getStatusEffects().addDecayingStatusEffect(root);
        activeUnit.getStatusEffects().addDecayingStatusEffect(doubleRange);
        activeUnit.setActionToken(0);
        activeUnit.setMovementToken(false);
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
