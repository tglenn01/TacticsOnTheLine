package main.model.graphics.menus;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.ConsumableAbility;
import main.model.graphics.sceneElements.buttons.AbilityButton;
import main.model.itemSystem.ConsumableItemInventory;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class AbilityMenu {
    private static boolean isDisplaying;
    private static Stage window;

    public static void display(CharacterUnit activeUnit, List<Ability> abilityList) {
        window = new Stage();
        window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        window.setTitle("Ability Menu");
        List<AbilityButton> abilityButtonList = new ArrayList<>();
        for (Ability ability : abilityList) {
            AbilityButton abilityButton = new AbilityButton(ability);
            abilityButtonList.add(abilityButton);
            abilityButton.setOnAction(e -> {
                window.close();
                if (ability.getClass() == ConsumableAbility.class) openItemMenu(activeUnit, window);
                else activeUnit.useAbility(ability);
            });
        }
        VBox node = new VBox();
        node.getChildren().addAll(abilityButtonList);
        Scene scene = new Scene(node);
        window.setOnCloseRequest(e -> isDisplaying = false);
        window.setOnHidden(e -> isDisplaying = false);
        window.setOnShown(e -> isDisplaying = true);
        window.setScene(scene);
        window.show();
    }

    public static boolean isDisplaying() {
        return isDisplaying;
    }

    public static void closeWindow() {
        window.close();
    }

    private static void openItemMenu(CharacterUnit activeUnit, Stage window) {
        if (ConsumableItemInventory.getInstance().isEmpty()) {
            Popup noItemsMessage = new Popup();
            noItemsMessage.getContent().add(new Label("No More Items, Choose Again"));
        } else {
            ItemMenu.display(activeUnit);
        }
    }
}
