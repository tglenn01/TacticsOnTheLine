package main.model.boardSystem;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.model.boardSystem.tiles.LandType;
import main.model.characterSystem.CharacterUnit;

public class BoardSpace extends Pane {
    private LandType landType;
    private CharacterUnit occupyingUnit;
    private int xCoordinate;
    private int yCoordinate;
    public final static int BOARD_SPACE_SIZE = 150;

    public BoardSpace(LandType landType, int xCoordinate, int yCoordinate) {
        setLandType(landType);
        setXValue(xCoordinate);
        setYValue(yCoordinate);
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
        this.getChildren().remove(occupyingUnit.getSprite());
        this.occupyingUnit = null;
    }

    public CharacterUnit getOccupyingUnit() {
        return this.occupyingUnit;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
        this.setBackground(landType.getTileColour());
    }


    public boolean isValidMovementSpace(BoardSpace currentSpace, int movement) {
        if (!this.landType.isTerrainable()) return false;
        if (this.occupyingUnit != null) return false;
        return isInRange(currentSpace, movement);
    }

    public boolean isValidAbilitySpace(BoardSpace currentSpace, int range) {
        return isInRange(currentSpace, range);
    }

    private boolean isInRange(BoardSpace currentSpace, int movement) {
        int simpleX = this.xCoordinate - currentSpace.getXCoordinate();
        int simpleY = this.yCoordinate - currentSpace.getYCoordinate();

        return (Math.abs(simpleX) + Math.abs(simpleY)) <= movement;
    }

    public void changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR colour) {
        this.setBackground(landType.highlightSpace(colour));
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

    public boolean isOccupied() {
        return this.occupyingUnit != null;
    }

    public LandType getLandType() {
        return this.landType;
    }
}
