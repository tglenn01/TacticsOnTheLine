package main.model.boardSystem.tiles;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GrassTile extends Tile {

    public GrassTile() {
    }

    protected void setTileColor() {
        this.tileColor = new Color(0, 0.5, 0, 1.0);
    }

    @Override
    protected void initializeBackground() {
        BackgroundFill background_fill = new BackgroundFill(this.tileColor,
                new CornerRadii(5), new Insets(0));
        this.background = new Background(background_fill);
    }


}
