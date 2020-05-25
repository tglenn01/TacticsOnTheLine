package main.model.boardSystem;

import javafx.scene.layout.Pane;
import main.model.boardSystem.tiles.Tile;
import main.model.characterSystem.CharacterUnit;

public class BoardSpace extends Pane {
    private Tile landType;
    private CharacterUnit occupyingUnit;
    private int xCoordinate;
    private int yCoordinate;
    public final static int BOARD_SPACE_SIZE = 50;

    public BoardSpace(Tile landType, int xCoordinate, int yCoordinate) {
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
        this.getChildren().add(occupyingUnit.getSprite());
        if (occupyingUnit.getBoardSpace() != this) {
            occupyingUnit.setBoardSpace(this);
        }
    }

    public CharacterUnit getOccupyingUnit() {
        return this.occupyingUnit;
    }

    public void setLandType(Tile landType) {
        this.landType = landType;
    }

    public void isValidSpace() {

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
