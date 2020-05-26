package main.model.graphics.icons;

import javafx.scene.control.Button;
import main.model.itemSystem.Consumable;

public class ItemButton extends Button {
    private Consumable consumable;

    public ItemButton(Consumable consumable) {
        this.consumable = consumable;
        this.setText(consumable.getConsumableName());
    }

    public Consumable getConsumable() {
        return this.consumable;
    }
}
