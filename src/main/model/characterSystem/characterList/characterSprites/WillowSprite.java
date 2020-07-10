package main.model.characterSystem.characterList.characterSprites;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.sceneElements.images.CharacterSprite;

public class WillowSprite extends CharacterSprite {

    public WillowSprite(CharacterUnit unit) {
        super(unit);
    }

    @Override
    protected void initializeSpriteArray() {
        String path = "resources/willowCharacterSprites/";
        spriteArray[0][0] = new Image(path + "willow_up_stand.png");
        spriteArray[0][1] = new Image(path + "willow_up_walk1.png");
        spriteArray[0][2] = new Image(path + "willow_up_walk2.png");
        spriteArray[1][0] = new Image(path + "willow_right_stand.png");
        spriteArray[1][1] = new Image(path + "willow_right_walk1.png");
        spriteArray[1][2] = new Image(path + "willow_right_walk2.png");
        spriteArray[2][0] = new Image(path + "willow_down_stand.png");
        spriteArray[2][1] = new Image(path + "willow_down_walk1.png");
        spriteArray[2][2] = new Image(path + "willow_down_walk2.png");
        spriteArray[3][0] = new Image(path + "willow_left_stand.png");
        spriteArray[3][1] = new Image(path + "willow_left_walk1.png");
        spriteArray[3][2] = new Image(path + "willow_left_walk2.png");
    }
}
