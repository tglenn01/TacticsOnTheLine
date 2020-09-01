package main.model.graphics.menus;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

import static main.model.graphics.DefaultScene.CSS_FILE;

public class AbilityPreview extends Stage {

    public AbilityPreview(CharacterUnit activeUnit, CharacterUnit receivingUnit, Ability chosenAbility) {
        GridPane layout = new GridPane();

        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        Label healthLabel = new Label("Health:");
        Label effectLabel = new Label(chosenAbility.getEffectType());
        Label percentHitLabel = new Label("Hit %");
        Label healthValue = new Label(Integer.toString(receivingUnitStatSheet.getHealth()));
        healthValue.setAlignment(Pos.CENTER);
        Node effectValue = chosenAbility.getExpectedResultsLabel(activeUnit, receivingUnit);
        effectValue.autosize();
        Label percentHitValue = new Label(Integer.toString(chosenAbility.getHitChance(activeUnit, receivingUnit)));

        layout.add(healthLabel, 0, 0);
        layout.add(healthValue, 0, 1);
        layout.add(effectLabel, 1, 0);
        layout.add(effectValue, 1, 1);
        layout.add(percentHitLabel, 2, 0);
        layout.add(percentHitValue, 2, 1);

        layout.setVgap(16.00);
        layout.setHgap(16.00);
        layout.getChildren().forEach(node -> node.setId("normalNode"));
        layout.getStylesheets().add(CSS_FILE);
        layout.autosize();

        Scene scene = new Scene(layout);
        this.setResizable(false);
        this.initStyle(StageStyle.UTILITY);
        this.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        this.setScene(scene);
        this.setX(receivingUnit.getCharacterSprite().getLayoutX());
        this.setY(receivingUnit.getCharacterSprite().getLayoutY());
        this.show();
    }
}
