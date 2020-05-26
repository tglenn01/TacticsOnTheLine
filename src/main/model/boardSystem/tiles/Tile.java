package main.model.boardSystem.tiles;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class Tile {
    protected Color tileColor;
    protected Background background;

    public Tile() {
        setTileColor();
        initializeBackground();
    }

    protected abstract void setTileColor();

    protected void initializeBackground() {
        BackgroundFill background_fill = new BackgroundFill(this.tileColor,
                new CornerRadii(5), new Insets(0));
        this.background = new Background(background_fill);
    }

    public Background getTileColour() {
        return this.background;
    }

    public Background highlightSpace() {
        Color highlightColor = new Color(0.5, 0, 0, 1.0);
        BackgroundFill highlightFill = new BackgroundFill(highlightColor,
                new CornerRadii(5), new Insets(0));
        return new Background(highlightFill);
    }

    public Background unHighlightedSpace() {
        return background;
    }

    public Background hoveredSpace() {
        Color highlightColor = new Color(0.5, 0.5, 0, 1.0);
        BackgroundFill highlightFill = new BackgroundFill(highlightColor,
                new CornerRadii(5), new Insets(0));
        return new Background(highlightFill);
    }

}
