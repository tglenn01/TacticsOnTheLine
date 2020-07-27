package main.model.graphics.sceneElements.images;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.DefaultScene;

public class CharacterNameLabel extends Pane {
    private Label name;

    public CharacterNameLabel(CharacterUnit unit, int minXSize, int minYSize) {
        this.name = new Label();
        name.setText(unit.getCharacterName());
        name.setAlignment(Pos.CENTER);
        DefaultScene.centreRegionOnPane(this, name);
        this.setMinSize(minXSize, minYSize);
        this.getChildren().add(name);
    }

    public void updateLabel(CharacterUnit unit) {
        this.name.setText(unit.getCharacterName());
    }
}

