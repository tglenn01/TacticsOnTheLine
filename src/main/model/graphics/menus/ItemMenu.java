package main.model.graphics.menus;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.characterSystem.CharacterUnit;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;

import java.util.ArrayList;
import java.util.List;

import static main.model.graphics.DefaultScene.CSS_FILE;

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
            Button itemButton  = new Button(consumable.getConsumableName());
            itemButton.setOnMousePressed(new ItemButtonHandler(activeUnit, battleMenu, consumable, itemButton));
            itemButtonList.add(itemButton);
        }
        return itemButtonList;
    }

    private static class ItemButtonHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private BattleMenu battleMenu;
        private Consumable consumable;
        private Button itemButton;

        private ItemButtonHandler(CharacterUnit activeUnit, BattleMenu battleMenu, Consumable consumable, Button itemButton) {
            this.activeUnit = activeUnit;
            this.battleMenu = battleMenu;
            this.consumable = consumable;
            this.itemButton = itemButton;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                battleMenu.close();
                activeUnit.useItem(consumable);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                ItemDescription itemDescription = new ItemDescription(consumable, event.getScreenX(), event.getScreenY());
                itemButton.setOnMouseReleased(e -> itemDescription.close());
            }
        }
    }

    private static class ItemDescription extends Stage {
        private ItemDescription(Consumable consumable, double xCord, double yCord) {

            Label itemName = new Label("Name: " + consumable.getConsumableName());
            Label potency = new Label("Potency: " + consumable.getPotency());
            Label description = new Label("Description: " + consumable.getDescription());
            description.setWrapText(true);

            VBox layout = new VBox(itemName, potency, description);
            layout.setId("normalNode");
            layout.getStylesheets().add(CSS_FILE);
            this.setScene(new Scene(layout));
            this.initStyle(StageStyle.UTILITY);
            this.setWidth(250);
            this.setHeight(165);
            this.setX(xCord - 100);
            this.setY(yCord - 50);
            this.show();
        }
    }
}
