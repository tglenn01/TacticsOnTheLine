package main.model.characterSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class CharacterSprite {
    public static final String ESTELLE_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\estelleSprite.png";
    public static final String JOSHUA_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\joshuaSprite.png";
    public static final String KLOE_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\kloeSprite.png";
    public static final String CASSIUS_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\cassiusSprite.png";
    public static final String ENEMY_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\enemySprite.png";
    private ImageView sprite;
    private CharacterUnit unit;
    private Image image;

    public CharacterSprite(CharacterUnit unit, String fileLocation) {
        initializeSprite(fileLocation);
    }

    private void initializeSprite(String fileLocation) {
        try {
            FileInputStream input = new FileInputStream(fileLocation);
            this.image = new Image(input);
            this.sprite = new ImageView(image);
        } catch (Exception e) {
            System.out.println("Portrait not found");
        }
    }

    public ImageView getSprite() {
        return this.sprite;
    }

    public Image getImage() {
        return this.image;
    }

    public CharacterUnit getUnit() {
        return unit;
    }
}
