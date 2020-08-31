package main.model.graphics.sceneElements.list;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.combatSystem.Ability;
import main.model.graphics.DefaultScene;
import main.model.graphics.sceneElements.images.AbilityImage;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

import static main.model.graphics.DefaultScene.CSS_FILE;

public class AbilitiesList extends Pane implements EventHandler<MouseEvent> {
    private List<AbilityImage> icons;
    private HBox hBox;


    public AbilitiesList(List<Ability> abilityList, int minXSize, int minYSize) {
        hBox = new HBox();
        icons = new ArrayList<>();
        for (Ability ability : abilityList) {
            if (ability.isUnique()) {
                AbilityImage icon = new AbilityImage(ability);
                icons.add(icon);
                icon.setId("abilityNameLabel");
                hBox.getChildren().add(icon);
                icon.setAlignment(Pos.CENTER);
                icon.setOnMousePressed(this);
            }
        }
        hBox.setSpacing(32);
        hBox.setAlignment(Pos.CENTER);


        DefaultScene.centreRegionOnPane(this, hBox);
        this.setPrefSize(minXSize, minYSize);
        this.getChildren().add(hBox);
    }

    public void updateData(List<Ability> abilityList) {
        hBox.getChildren().clear();
        for (Ability ability : abilityList) {
            if (ability.isUnique()) {
                AbilityImage icon = new AbilityImage(ability);
                icons.add(icon);
                icon.setId("abilityNameLabel");
                hBox.getChildren().add(icon);
                icon.setOnMousePressed(this);
            }
        }
    }

    @Override
    public void handle(MouseEvent event) {
        AbilityImage icon = (AbilityImage) event.getSource();
        AbilityDescription abilityDescription = new AbilityDescription(icon.getAbility());
        abilityDescription.display(event.getScreenX(), event.getScreenY());
        icon.setOnMouseReleased(e -> abilityDescription.window.close());
    }


    private static class AbilityDescription {
        private Ability ability;
        private Stage window;

        private AbilityDescription(Ability ability) {
            this.ability = ability;
        }

        private void display(double xCord, double yCord) {
            this.window = new Stage();
            window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());

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
            window.initStyle(StageStyle.UTILITY);
            window.setScene(scene);
            window.setWidth(400);
            window.setHeight(250);
            window.setX(xCord - 100);
            window.setY(yCord - 50);
            window.show();
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
}
