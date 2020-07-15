package main.model.graphics.sceneElements.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class CharacterPortrait {
    public static final String ESTELLE_PORTRAIT = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\estellePortrait.jpg";
    private ImageView portrait;
    private Image image;

    public CharacterPortrait(String fileLocation) {
        initializePortrait(fileLocation);
    }

    private void initializePortrait(String fileLocation) {
        try {
            FileInputStream input = new FileInputStream(fileLocation);
            this.image = new Image(input);
            this.portrait = new ImageView(image);
        } catch (Exception e) {
            System.out.println("Portrait not found");
        }
    }

    public ImageView getPortrait() {
        return this.portrait;
    }

    public Image getImage() {
        return this.image;
    }
}
