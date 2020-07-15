package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.MenuOpenedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.*;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.itemSystem.ResourceReplenishBonus;
import main.ui.TacticBaseBattle;

import java.util.List;

public class ConsumableAbility extends SupportiveAbility {

    public ConsumableAbility() {
        super("Item", 0, 1, 1,
                Ability.AbilityType.ITEM, "Use an item on an ally");
    }

    public void getTargets(CharacterUnit activeUnit, Consumable item) throws MenuOpenedException {
        List<BoardSpace> possibleBoardSpaces = getBoardSpaces(activeUnit);
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        currentBoard.displayAbilitySpaces(possibleBoardSpaces);

        setHandlers(activeUnit, possibleBoardSpaces);
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
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces)
            throws AttackMissedException, UnitIsDeadException {
        System.out.println("Must have an item");
    }

    public void takeAction(Consumable item, List<BoardSpace> targetedBoardSpaces) {
         for (BoardSpace boardSpace : targetedBoardSpaces) {
             CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
             StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
             if (item.getAbilityType() == AbilityType.HEAL) {
                 healUnit(receivingUnit, receivingUnitStatSheet, item);
             } else if (item.getAbilityType() == AbilityType.MANA_GAIN) {
                 gainMana(receivingUnit, receivingUnitStatSheet, item);
             } else if (item.getAbilityType() == AbilityType.ATTACK_BUFF) {
                 receivingUnit.getStatusEffects().addDecayingStatusEffect(new AttackBuff(receivingUnit, item.getPotency(), item.getDuration()));
             } else if (item.getAbilityType() == AbilityType.DEFENSE_BUFF) {
                 receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseBuff(receivingUnit, item.getPotency(), item.getDuration()));
             } else if (item.getAbilityType() == AbilityType.ATTACK_DEBUFF) {
                 receivingUnit.getStatusEffects().addDecayingStatusEffect(new AttackDebuff(receivingUnit, item.getPotency(), item.getDuration()));
             } else if (item.getAbilityType() == AbilityType.DEFENSE_DEBUFF) {
                 receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseDebuff(receivingUnit, item.getPotency(), item.getDuration()));
             } else if (item.getAbilityType() == AbilityType.INVULNERABLE) {
                 receivingUnit.getStatusEffects().addPermanentStatusEffect(new Invulnerable(receivingUnit, item.getPotency()));
             } else if (item.getAbilityType() == AbilityType.ROOT) {
                 receivingUnit.getStatusEffects().addDecayingStatusEffect(new Root(receivingUnit, item.getPotency(), item.getDuration()));
             }
         }

        ConsumableItemInventory.getInstance().removeConsumableItem(item);
    }

    @Override
    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return bonus.getHealingBonus();
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return bonus.getManaGainBonus();
    }

    public boolean targetsAlly() {
        return true;
    }

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range);
    }
}
