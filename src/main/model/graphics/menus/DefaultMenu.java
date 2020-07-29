package main.model.graphics.menus;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.model.characterSystem.CharacterUnit;

import java.util.List;

public abstract class DefaultMenu {
    protected final int buttonWidth = 100;
    protected final int buttonHeight = 50;

    protected abstract List<Button> setButtons(CharacterUnit unit, BattleMenu battleMenu);

    public Scene getMenu(CharacterUnit activeUnit, BattleMenu battleMenu) {
        List<Button> buttonList = setButtons(activeUnit, battleMenu);
        applyButtonStyle(buttonList);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttonList);
        return new Scene(vBox);
    }

    protected void applyButtonStyle(List<Button> buttonList) {
        for (Button button : buttonList) {
            button.setPrefSize(buttonWidth, buttonHeight);
        }
    }

    protected Button getReturnButton(CharacterUnit activeUnit, BattleMenu battleMenu) {
        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> {
            battleMenu.displayCharacterMenu(activeUnit);
        });
        return returnButton;
    }
}
