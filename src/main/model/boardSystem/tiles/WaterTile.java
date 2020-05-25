package main.model.boardSystem.tiles;

import javafx.scene.paint.Color;

public class WaterTile extends Tile {

    public WaterTile() {
        Color tileColor = new Color(0, 0, 0.5, 1.0);
        this.setFill(tileColor);
    }
}
