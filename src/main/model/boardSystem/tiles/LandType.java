package main.model.boardSystem.tiles;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class LandType {
    public enum BOARD_SPACE_HIGHLIGHT_COLOUR {NORMAL, MOVEMENT_HIGHLIGHT_COLOUR, ABILITY_HIGHLIGHT_COLOUR}
    protected Color tileColor;
    protected Background background;
    protected Color movementHighlightColour = new Color(0.5, 0, 0, 0.5);
    protected Color abilityHighlightColour = new Color(0.5, 0.5, 0, 0.5);


    public LandType() {
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

    public Background highlightSpace(BOARD_SPACE_HIGHLIGHT_COLOUR colour) {
        Color newColour;
        if (colour == BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR) newColour = abilityHighlightColour;
        else if (colour == BOARD_SPACE_HIGHLIGHT_COLOUR.MOVEMENT_HIGHLIGHT_COLOUR) newColour = movementHighlightColour;
        else newColour = this.tileColor;
        BackgroundFill highlightFill = new BackgroundFill(newColour,
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
