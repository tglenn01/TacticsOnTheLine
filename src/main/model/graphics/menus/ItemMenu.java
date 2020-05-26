package main.model.graphics.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.icons.ItemButton;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.Battle;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu implements EventHandler<ActionEvent> {
    private CharacterUnit activeUnit;
    private Battle battle;

    public ItemMenu(Battle battle, CharacterUnit activeUnit) {
        this.activeUnit = activeUnit;
        this.battle = battle;
        Popup window = new Popup();

        List<Button> itemList = new ArrayList<>();
        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            ItemButton itemButton  = new ItemButton(consumable);
            itemButton.setOnAction(this);
            itemList.add(itemButton);
        }

        VBox node = new VBox();
        node.getChildren().addAll(itemList);

        ScrollPane parent = new ScrollPane();
        parent.setContent(node);

        window.getContent().addAll(parent);
    }

    @Override
    public void handle(ActionEvent event) {
        ItemButton itemButton = (ItemButton) event.getSource();
        Consumable item = itemButton.getConsumable();
        activeUnit.useItem(battle, item);
    }
}
