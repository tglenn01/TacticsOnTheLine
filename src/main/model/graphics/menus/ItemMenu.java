package main.model.graphics.menus;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.icons.ItemButton;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.Battle;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu {

    public static void display(Battle battle, CharacterUnit activeUnit) {
        Popup window = new Popup();

        List<Button> itemList = new ArrayList<>();
        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            ItemButton itemButton  = new ItemButton(consumable);
            itemButton.setOnAction(e -> {
                Consumable item = itemButton.getConsumable();
                activeUnit.useItem(battle, item);
            });
            itemList.add(itemButton);
        }

        VBox node = new VBox();
        node.getChildren().addAll(itemList);

        ScrollPane parent = new ScrollPane();
        parent.setContent(node);

        window.getContent().addAll(parent);
        window.show(TacticBaseBattle.getInstance().getPrimaryStage());
    }
}
