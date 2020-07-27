package main.model.boardSystem.tiles;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class WoodsLandType extends LandType {

    public WoodsLandType() {
    }

    protected void setTileColor() {
        this.tileColor = new Color(0, .30, 0, 1);
    }

    @Override
    protected void initializeBackground() {
        BackgroundFill background_fill = new BackgroundFill(this.tileColor,
                new CornerRadii(5), new Insets(0));
        this.background = new Background(background_fill);
    }

}