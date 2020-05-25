package main.model.graphics.icons;

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
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class AbilityIcons extends HBox implements EventHandler<MouseEvent> {
    private List<AbilityIcon> icons;

    public AbilityIcons(List<Ability> abilityList) {
        this.icons = new ArrayList<>();
        for (Ability ability : abilityList) {
            if (ability.isUnique()) {
                AbilityIcon icon = new AbilityIcon(ability);
                icons.add(icon);
                this.getChildren().add(icon);
                icon.setPrefSize(100, 100);
                icon.setOnMouseEntered(this);
            }
        }
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(600, 120);
    }

    @Override
    public void handle(MouseEvent event) {
        for (AbilityIcon icon : icons) {
            if (event.getSource() == icon) {
                AbilityDescription abilityDescription = new AbilityDescription(icon.getAbility());
                abilityDescription.display(event.getScreenX(), event.getScreenY());
            }
        }
    }


    private static class AbilityDescription implements EventHandler<MouseEvent> {
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
            abilityDescription.setOnMouseExited(this);
            abilityDescription.getChildren().addAll(abilityName, manaCost, description);
            Scene scene = new Scene(abilityDescription);
            window.setScene(scene);
            window.setWidth(350);
            window.setHeight(150);
            window.setX(xCord - 100);
            window.setY(yCord - 50);
            window.show();
        }

        @Override
        public void handle(MouseEvent event) {
            window.close();
        }
    }

}
