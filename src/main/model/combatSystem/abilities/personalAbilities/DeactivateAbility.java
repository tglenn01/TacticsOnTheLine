package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.SupportiveAbility;
import main.model.itemSystem.ResourceReplenishBonus;

import java.util.List;

public class DeactivateAbility extends SupportiveAbility {
    private CharacterUnit activeUnit;

    public DeactivateAbility() {
        super("Deactivate", 0, 0, 1, Ability.AbilityType.HEAL,
                "Pass your turn to heal to full");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) throws AttackMissedException, UnitIsDeadException {
        this.activeUnit = activeUnit;

        for (BoardSpace boardSpace : targetedBoardSpaces) {
            CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
            healUnit(receivingUnit, receivingUnit.getCharacterStatSheet(), activeUnit.getCharacterStatSheet());
        }

        activeUnit.setMovementToken(false);
        activeUnit.setActionToken(0);
    }

    @Override
    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return activeUnit.getCharacterStatSheet().getMaxHealth();
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return 0;
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
