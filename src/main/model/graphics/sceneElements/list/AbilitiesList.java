package main.model.graphics.sceneElements.list;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.model.combatSystem.Ability;
import main.model.graphics.DefaultScene;
import main.model.graphics.sceneElements.images.AbilityDescription;
import main.model.graphics.sceneElements.images.AbilityImage;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesList extends Pane implements EventHandler<MouseEvent> {
    private List<AbilityImage> icons;
    private HBox descriptionLayout;


    public AbilitiesList(List<Ability> abilityList, int minXSize, int minYSize) {
        descriptionLayout = new HBox();
        icons = new ArrayList<>();
        for (Ability ability : abilityList) {
            if (ability.isUnique()) {
                AbilityImage icon = new AbilityImage(ability);
                icons.add(icon);
                icon.setId("abilityNameLabel");
                descriptionLayout.getChildren().add(icon);
                icon.setAlignment(Pos.CENTER);
                icon.setOnMousePressed(this);
            }
        }
        descriptionLayout.setSpacing(32);
        descriptionLayout.setAlignment(Pos.CENTER);


        DefaultScene.centreRegionOnPane(this, descriptionLayout);
        this.setPrefSize(minXSize, minYSize);
        this.getChildren().add(descriptionLayout);
    }

    public void updateData(List<Ability> abilityList) {
        descriptionLayout.getChildren().clear();
        for (Ability ability : abilityList) {
            if (ability.isUnique()) {
                AbilityImage icon = new AbilityImage(ability);
                icons.add(icon);
                icon.setId("abilityNameLabel");
                descriptionLayout.getChildren().add(icon);
                icon.setAlignment(Pos.CENTER);
                icon.setOnMousePressed(this);
            }
        }
    }

    @Override
    public void handle(MouseEvent event) {
        AbilityImage icon = (AbilityImage) event.getSource();
        AbilityDescription abilityDescription = new AbilityDescription(icon.getAbility());
        abilityDescription.display(event.getScreenX(), event.getScreenY());
        icon.setOnMouseReleased(e -> abilityDescription.close());
    }
}
