package main.model.graphics.menus;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.sceneElements.buttons.ItemButton;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu {

    public static void display(CharacterUnit activeUnit) {
        Stage window = new Stage();
        window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        window.setTitle("Item Menu");

        Button returnButton = new Button("return");
        returnButton.setOnAction(e -> {
            window.hide();
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            AbilityMenu.display(activeUnit, activeUnit.getCharacterJob().getJobAbilityList());
        });

        List<Button> itemList = new ArrayList<>();
        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            ItemButton itemButton  = new ItemButton(consumable);
            itemButton.setOnAction(e -> {
                window.close();
                Consumable item = itemButton.getConsumable();
                activeUnit.useItem(item);
            });
            itemList.add(itemButton);
        }

        VBox node = new VBox();
        node.getChildren().add(returnButton);
        node.getChildren().addAll(itemList);

        Scene scene = new Scene(node);
        window.setScene(scene);
        window.show();
    }
}
