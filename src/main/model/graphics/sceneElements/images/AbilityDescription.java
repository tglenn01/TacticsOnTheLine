package main.model.graphics.sceneElements.images;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

import static main.model.graphics.DefaultScene.CSS_FILE;

public class AbilityDescription extends Stage {
    private Ability ability;

    public AbilityDescription(Ability ability) {
        this.ability = ability;
        this.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        this.initStyle(StageStyle.UTILITY);
        this.setWidth(400);
        this.setHeight(250);

    }

    public void display(double xCord, double yCord) {


        BorderPane abilityDescriptionPane = new BorderPane();
        VBox informationLayout = setInformationLayout();
        VBox targetTypeLayout = setTargetTypeLayout();

        informationLayout.setPrefSize(175, 120);
        targetTypeLayout.setPrefSize(175, 120);
        abilityDescriptionPane.setLeft(informationLayout);
        abilityDescriptionPane.setCenter(targetTypeLayout);

        abilityDescriptionPane.setId("normalNode");
        Scene scene = new Scene(abilityDescriptionPane);
        scene.getStylesheets().add(CSS_FILE);
        this.setScene(scene);
        this.setX(xCord - this.getWidth() - 25);
        this.setY(yCord - (this.getHeight() / 2));
        this.show();
    }


    private VBox setInformationLayout() {
        Label abilityName = new Label("Name: " + this.ability.getAbilityName());
        Label manaCost = new Label("Mana: " + this.ability.getManaCost());
        Label abilityRange = new Label("Range: " + this.ability.getRange());
        Label effectType = new Label("Effect: " + this.ability.getEffectType());
        Label description = new Label(this.ability.getAbilityDescription());
        description.setWrapText(true);

        return new VBox(abilityName, manaCost, abilityRange, effectType, description);
    }

    private VBox setTargetTypeLayout() {
        ImageView targetTypeImage = this.ability.getTargetType().getTargetTypeImage();
        targetTypeImage.setFitHeight(150);
        targetTypeImage.setPreserveRatio(true);
        VBox imageLayout = new VBox(targetTypeImage);
        imageLayout.setAlignment(Pos.CENTER);
        return imageLayout;
    }
}