package main.model.graphics.sceneElements.images;

import javafx.scene.control.Label;
import main.model.characterSystem.CharacterUnit;

public class CharacterNameLabel extends Label {

    public CharacterNameLabel(CharacterUnit unit, int minXSize, int minYSize) {
        this.setText(unit.getCharacterName());
        this.setMinSize(minXSize, minYSize);
    }
}

