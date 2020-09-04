package main.model.graphics.sceneElements.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPortrait {
    private ImageView portrait;
    private Image image;

    public CharacterPortrait(String fileLocation) {
        initializePortrait(fileLocation);
    }

    private void initializePortrait(String fileLocation) {
        this.image = new Image(fileLocation);
        this.portrait = new ImageView(image);
    }

    public ImageView getPortrait() {
        return this.portrait;
    }

    public Image getImage() {
        return this.image;
    }
}
