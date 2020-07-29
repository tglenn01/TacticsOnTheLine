package main.model.graphics.menus;

import javafx.scene.control.Button;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.sceneElements.buttons.ItemButton;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu extends DefaultMenu{

    @Override
    protected List<Button> setButtons(CharacterUnit activeUnit, BattleMenu battleMenu) {
        List<Button> itemButtonList = getItemButtons(activeUnit, battleMenu);

        Button returnButton = getReturnButton(activeUnit, battleMenu);
        itemButtonList.add(returnButton);

        return itemButtonList;
    }

    private List<Button> getItemButtons(CharacterUnit activeUnit, BattleMenu battleMenu) {
        List<Button> itemButtonList = new ArrayList<>();
        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            ItemButton itemButton  = new ItemButton(consumable);
            itemButton.setOnAction(e -> {
                battleMenu.close();
                Consumable item = itemButton.getConsumable();
                activeUnit.useItem(item);
            });
            itemButtonList.add(itemButton);
        }
        return itemButtonList;
    }
}
