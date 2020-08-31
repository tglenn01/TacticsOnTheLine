package main.model.combatSystem.abilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.NPC;
import main.model.combatSystem.targetTypes.ItemTarget;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;

import java.util.List;

public class ConsumableAbility extends SupportiveAbility {

    public ConsumableAbility() {
        super("Item", 0, 1, 1, "Use an item on an ally");
    }

    public void getTargets(CharacterUnit activeUnit, Consumable item) {
        ItemTarget itemTarget = (ItemTarget) this.targetType;
        List<BoardSpace> possibleBoardSpaces = itemTarget.getTargetPattern(activeUnit.getBoardSpace(), range, this);
        if (activeUnit.getClass() == NPC.class);
        else {
            itemTarget.displayTargets(activeUnit, this, possibleBoardSpaces);
            itemTarget.setHandlers(activeUnit, item, possibleBoardSpaces);
        }
    }

    public void takeAction(Consumable item, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            if (boardSpace.isOccupied()) {
                CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
                item.applyItem(receivingUnit);
            }
        }
        ConsumableItemInventory.getInstance().removeConsumableItem(item);
    }

    @Override
    public String getEffectType() {
        return "Item";
    }

    @Override
    protected void setTargetType() {
        this.targetType = new ItemTarget();
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
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }
}
