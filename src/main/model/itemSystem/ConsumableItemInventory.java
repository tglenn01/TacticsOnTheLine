package main.model.itemSystem;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConsumableItemInventory implements Iterable<Consumable> {
    private static ConsumableItemInventory consumableItemInventory;
    private List<Consumable> consumableList;
    private ConsumableAbility itemAbility;

    private ConsumableItemInventory() {
        consumableList = new ArrayList<>();
        itemAbility= new ConsumableAbility("Item", 0, 1, 1,
                Ability.AbilityType.ITEM, "Use an item on an ally");
    }

    public static ConsumableItemInventory getInstance() {
        if (consumableItemInventory == null) {
            consumableItemInventory = new ConsumableItemInventory();
        }
        return consumableItemInventory;
    }

    public void addConsumableItem(Consumable consumable) {
        consumableList.add(consumable);
    }

    public void removeConsumableItem(Consumable consumable) { consumableList.remove(consumable); }

    public ConsumableAbility getItemAbility() {
        return itemAbility;
    }

    @Override
    public Iterator<Consumable> iterator() {
        return consumableList.iterator();
    }

    public boolean isEmpty() {
        return consumableList.isEmpty();
    }
}
