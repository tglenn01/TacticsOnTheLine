package main.model.characterSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class CharacterPortrait {
    private ImageView portrait;

    public CharacterPortrait() {
        try {
            FileInputStream input = new FileInputStream("D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\estellePortrait.jpg");
            Image image = new Image(input);
            this.portrait = new ImageView(image);
        } catch (Exception e) {
            System.out.println("Portrait not found");
        }
    }

    public ImageView getPortrait() {
        return this.portrait;
    }
}
