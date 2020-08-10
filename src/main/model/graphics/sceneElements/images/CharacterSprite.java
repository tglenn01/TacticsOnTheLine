package main.model.graphics.sceneElements.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.Board;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.menus.CharacterStatsMenu;
import main.ui.TacticBaseBattle;

public abstract class CharacterSprite extends ImageView {
    private CharacterUnit unit;
    protected Image[][] spriteArray = new Image[4][3]; // 4 directions and 3 different animations per direction
    public double duration = .50;

    public CharacterSprite(CharacterUnit unit) {
        this.unit = unit;
        initializeSpriteArray();
        initializeSprite();
    }

    protected abstract void initializeSpriteArray();

    private void initializeSprite() {
        this.setImage(spriteArray[2][0]); // default down looking
        this.setSpriteHandlers();
    }

    public ImageView getSprite() {
        return this;
    }

    public Image getStillImage() {
        return this.spriteArray[2][0];
    }


    // // 0 = up, 1 = right, 2 = down, 3 = left
    public void updateImage(double time, int direction) {
        int index = (int) ((time % (2 * duration)) / duration); // will alternate between 0 and 1
        this.setImage(spriteArray[direction][index + 1]); // 1 and 2 are the walking animations so 1 is added
    }

    public CharacterUnit getUnit() {
        return this.unit;
    }


    // If active and not showing character menu, clicking on unit will open the menu
    // If non-active and left-click, toggle between displaying their movement range
    // If non-active and right-click, open their stats menu
    public void setSpriteHandlers() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
            if (TacticBaseBattle.getInstance().getBattle().getActiveCharacter() == unit &&
                    !currentBoard.isAbilitySpacesShowing()) {
                if (!BattleMenu.getInstance().isShowing()) {
                    BattleMenu.getInstance().displayCharacterMenu(unit);
                }
            } else if (!currentBoard.isAbilitySpacesShowing() &&
                    event.getButton() == MouseButton.PRIMARY) {
                if (unit.isMovementRangeIsVisable()) {
                    currentBoard.stopShowingMovementSpaces(unit);
                    unit.setMovementRangeIsVisable(false);
                } else {
                    currentBoard.displayMovementSpaces(unit, currentBoard.getMovementArea(unit));
                    unit.setMovementRangeIsVisable(true);
                }
            } else if (!currentBoard.isAbilitySpacesShowing() &&
                    event.getButton() == MouseButton.SECONDARY) {
                if (!CharacterStatsMenu.isDisplaying()) CharacterStatsMenu.display(unit);
            }
        });
    }
}
