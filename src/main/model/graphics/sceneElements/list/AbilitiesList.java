package main.model.graphics.sceneElements.list;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import main.model.combatSystem.Ability;
import main.model.graphics.DefaultScene;
import main.model.graphics.sceneElements.images.AbilityImage;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

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
                icon.setId("abilityNameLabels");
                hBox.getChildren().add(icon);
                icon.setAlignment(Pos.CENTER);
                //icon.setPrefSize(100, 100);
                icon.setOnMousePressed(this);
            }
        }
        hBox.setSpacing(32);
        hBox.setAlignment(Pos.CENTER);
        hBox.setId("characterSelectElement");


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
                hBox.getChildren().add(icon);
                //icon.setPrefSize(100, 100);
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

            Pane abilityDescription = new TilePane();
            Label abilityName = new Label("Name: " + this.ability.getAbilityName());
            Label manaCost = new Label("Mana Cost " + this.ability.getManaCost());
            Label description = new Label(this.ability.getAbilityDescription());
            abilityDescription.setId("characterSelectElement");
            abilityDescription.getChildren().addAll(abilityName, manaCost, description);
            Scene scene = new Scene(abilityDescription);
            window.setScene(scene);
            window.setWidth(350);
            window.setHeight(150);
            window.setX(xCord - 100);
            window.setY(yCord - 50);
            window.show();
        }
    }
}
