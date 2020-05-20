package main.model.boardSystem;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.model.characterSystem.CharacterUnit;

public class BoardSpace {
    public enum LandType {GRASS, WATER};
    public static int BOARD_SPACE_SIZE = 50;
    private CharacterUnit occupyingUnit;
    private int xCoordinate;
    private int yCoordinate;
    private LandType landType;

    public BoardSpace(int xCoordinate, int yCoordinate) {
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
        if (occupyingUnit.getBoardSpace() != this) {
            occupyingUnit.setBoardSpace(this.xCoordinate, this.yCoordinate);
        }
    }

    public CharacterUnit getOccupyingUnit() {
        return this.occupyingUnit;
    }

    public void setLandType(LandType landType) {
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

    public Rectangle getSpace() {
        Rectangle space = new Rectangle();
        if (this.landType == LandType.GRASS) {
            Paint green = new Color(0, 200, 0, 100.00);
            space.setFill(green);
        } if (this.landType == LandType.WATER) {
            Paint blue = new Color(0, 0, 200, 100.00);
            space.setFill(blue);
        }
        space.setHeight(BOARD_SPACE_SIZE);
        space.setWidth(BOARD_SPACE_SIZE);
        return space;
    }
}
