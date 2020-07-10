package main.model.characterSystem.characterList.characterSprites;

import javafx.scene.image.Image;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.sceneElements.images.CharacterSprite;

public class No1Sprite extends CharacterSprite {
    public No1Sprite(CharacterUnit unit) {
        super(unit);
    }

    @Override
    protected void initializeSpriteArray() {
        String path = "resources/no1CharacterSprites/";
        spriteArray[0][0] = new Image(path + "no1_up_stand.png");
        spriteArray[0][1] = new Image(path + "no1_up_walk1.png");
        spriteArray[0][2] = new Image(path + "no1_up_walk2.png");
        spriteArray[1][0] = new Image(path + "no1_right_stand.png");
        spriteArray[1][1] = new Image(path + "no1_right_walk1.png");
        spriteArray[1][2] = new Image(path + "no1_right_walk2.png");
        spriteArray[2][0] = new Image(path + "no1_down_stand.png");
        spriteArray[2][1] = new Image(path + "no1_down_walk1.png");
        spriteArray[2][2] = new Image(path + "no1_down_walk2.png");
        spriteArray[3][0] = new Image(path + "no1_left_stand.png");
        spriteArray[3][1] = new Image(path + "no1_left_walk1.png");
        spriteArray[3][2] = new Image(path + "no1_left_walk2.png");
    }
}
