package main.model.boardSystem.landTypes;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class LandType {
    public enum BOARD_SPACE_HIGHLIGHT_COLOUR {NORMAL, NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR, ABILITY_HIGHLIGHT_COLOUR, HOVER_HIGHLIGHT_COLOR}

    protected Color tileColor;
    protected Background background;
    protected Color movementHighlightColour = Color.valueOf("#1450FF");
    protected Color abilityHighlightColour = Color.valueOf("#F7C625");
    protected Color hoverHighlightColour = Color.valueOf("#a67b05");


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
        else if (colour == BOARD_SPACE_HIGHLIGHT_COLOUR.NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR)
            newColour = movementHighlightColour;
        else if (colour == BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR) newColour = hoverHighlightColour;
        else newColour = this.tileColor;
        BackgroundFill highlightFill = new BackgroundFill(newColour,
                new CornerRadii(5), new Insets(0));

        return new Background(highlightFill);
    }

    public Background hoveredSpace() {
        BackgroundFill highlightFill = new BackgroundFill(hoverHighlightColour,
                new CornerRadii(5), new Insets(0));
        return new Background(highlightFill);
    }

    public boolean isTerrainable() {
        return this.getClass() == GrassLandType.class;
    }
}

