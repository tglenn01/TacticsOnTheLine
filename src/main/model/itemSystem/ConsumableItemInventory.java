package main.model.itemSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConsumableItemInventory implements Iterable<Consumable> {
    private static ConsumableItemInventory consumableItemInventory;
    private List<Consumable> consumableList;

    private ConsumableItemInventory() {
        consumableList = new ArrayList<>();
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

    @Override
    public Iterator<Consumable> iterator() {
        return consumableList.iterator();
    }

    public boolean isEmpty() {
        return consumableList.isEmpty();
    }
}
