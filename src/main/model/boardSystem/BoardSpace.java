package main.model.boardSystem;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.battleSystem.TacticBaseBattle;

public class BoardSpace extends BorderPane {
    private LandType landType;
    private CharacterUnit occupyingUnit;
    private int xCoordinate;
    private int yCoordinate;
    public final static int BOARD_SPACE_SIZE = 150;

    public BoardSpace(LandType landType, int xCoordinate, int yCoordinate) {
        setLandType(landType);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public void setOccupyingUnit(CharacterUnit occupyingUnit) {
        this.occupyingUnit = occupyingUnit;
        Pane spritePane = occupyingUnit.getCharacterSprite();
        this.setCenter(spritePane);
        this.setCenterShape(true);
        if (occupyingUnit.getBoardSpace() != this) {
            occupyingUnit.setBoardSpace(this);
        }
        try {
            TacticBaseBattle.getInstance().getCurrentBoard().updateAllMovementPreviews(occupyingUnit);
        } catch (NullPointerException e) {
            // ignore if the board is not initializedYet
        }

    }

    public void removeOccupyingUnit() {
        this.getChildren().remove(occupyingUnit.getSpriteImageView());
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

    public boolean isTerrainable() {
        if (!this.landType.isTerrainable()) return false;
        return this.occupyingUnit == null;
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
