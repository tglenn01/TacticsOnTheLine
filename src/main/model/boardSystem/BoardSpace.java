package main.model.boardSystem;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.model.boardSystem.tiles.Tile;
import main.model.boardSystem.tiles.WaterTile;
import main.model.characterSystem.CharacterUnit;

public class BoardSpace extends Pane {
    private Tile landType;
    private CharacterUnit occupyingUnit;
    private int xCoordinate;
    private int yCoordinate;
    public final static int BOARD_SPACE_SIZE = 150;
    private boolean selectable;

    public BoardSpace(Tile landType, int xCoordinate, int yCoordinate) {
        this.selectable = false;
        setLandType(landType);
        setXValue(xCoordinate);
        setYValue(yCoordinate);
        setUpActionEvent();
    }

    public void setXValue(int xValue) {
        this.xCoordinate = xValue;
    }

    public void setYValue(int yValue) {
        this.yCoordinate = yValue;
    }

    public void setOccupyingUnit(CharacterUnit occupyingUnit) {
        this.occupyingUnit = occupyingUnit;
        ImageView sprite = occupyingUnit.getSprite();
        sprite.fitWidthProperty().bind(this.widthProperty());
        sprite.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(sprite);
        if (occupyingUnit.getBoardSpace() != this) {
            occupyingUnit.setBoardSpace(this);
        }
    }

    public void removeOccupyingUnit() {
        this.occupyingUnit = null;
    }

    private void setUpActionEvent() {

    }

    public CharacterUnit getOccupyingUnit() {
        return this.occupyingUnit;
    }

    public void setLandType(Tile landType) {
        this.landType = landType;
        this.setBackground(landType.getTileColour());
    }

    public boolean isValidSpace(BoardSpace currentSpace, int movement) {
        if (this.landType.getClass() == WaterTile.class) return false;
        if (this.occupyingUnit != null) return false;

        int simpleX = this.xCoordinate - currentSpace.getXCoordinate();
        int simpleY = this.yCoordinate - currentSpace.getYCoordinate();

        return (Math.abs(simpleX) + Math.abs(simpleY)) <= movement;
    }

    public void highlightSpace(boolean highlighted) {
        this.selectable = highlighted;
        if (this.selectable) this.setBackground(landType.highlightSpace());
        else this.setBackground(landType.unHighlightedSpace());

    }

    public int getXCoordinate() {
        return this.xCoordinate;
    }

    public int getYCoordinate() {
        return this.yCoordinate;
    }

    public BoardSpace getBoardSpace() {
        return this;
    }
}
