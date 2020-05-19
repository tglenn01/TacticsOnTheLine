package main.model.boardSystem;

import main.model.characterSystem.CharacterUnit;

public class BoardSpace {
    public enum LandType {GRASS, WATER};
    private CharacterUnit occupyingUnit;
    private int xValue;
    private int yValue;
    private LandType landType;

    public BoardSpace(int xValue, int yValue) {
        setXValue(xValue);
        setYValue(yValue);
    }

    public void setXValue(int xValue) {
        this.xValue = xValue;
    }

    public void setYValue(int yValue) {
        this.yValue = yValue;
    }

    public void setOccupyingUnit(CharacterUnit occupyingUnit) {
        this.occupyingUnit = occupyingUnit;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

}
