package main.model.graphics.sceneElements.images;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.model.boardSystem.Board;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.StatusEffect;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.menus.CharacterStatsMenu;
import main.model.battleSystem.TacticBaseBattle;

import static main.model.boardSystem.BoardSpace.BOARD_SPACE_SIZE;

public abstract class CharacterSprite extends StackPane {
    private ImageView sprite;
    private CharacterUnit unit;
    protected Image[][] spriteArray = new Image[4][3]; // 4 directions and 3 different animations per direction
    private HBox statusEffectsLayout;
    public double duration = .50;

    public CharacterSprite(CharacterUnit unit) {
        this.unit = unit;
        initializeSpriteArray();
        initializeSprite();
    }

    protected abstract void initializeSpriteArray();

    private void initializeSprite() {
        sprite = new ImageView(spriteArray[2][0]); // default down looking
        sprite.fitWidthProperty().bind(this.widthProperty());
        sprite.fitHeightProperty().bind(this.heightProperty());
        sprite.setPreserveRatio(true);
        StackPane.setAlignment(sprite, Pos.CENTER);
        this.getChildren().add(sprite);
        setSpriteHandlers();
    }

    public void updateStatusEffectIndicatorToSprite() {
        if (statusEffectsLayout == null) statusEffectsLayout = new HBox();
        else statusEffectsLayout.getChildren().clear();
        if (!unit.getStatusEffects().iterator().hasNext()) {
            this.getChildren().remove(statusEffectsLayout);
            return;
        }
        for (StatusEffect statusEffect : unit.getStatusEffects()) {
            ImageView statusIcon = new ImageView(statusEffect.getIcon());
            statusIcon.setPreserveRatio(true);
            statusIcon.fitHeightProperty().bind(statusEffectsLayout.heightProperty());
            statusIcon.autosize();
            statusEffectsLayout.getChildren().add(statusIcon);
        }
        statusEffectsLayout.setOpacity(.80);
        statusEffectsLayout.setAlignment(Pos.CENTER_RIGHT);
        statusEffectsLayout.setSpacing(4.00);
        statusEffectsLayout.setMaxHeight(Integer.divideUnsigned(BOARD_SPACE_SIZE, 4));
        statusEffectsLayout.setMinHeight(Integer.divideUnsigned(BOARD_SPACE_SIZE, 4));
        statusEffectsLayout.setMaxWidth(BOARD_SPACE_SIZE);
        StackPane.setAlignment(statusEffectsLayout, Pos.BOTTOM_CENTER);
        if (!this.getChildren().contains(statusEffectsLayout)) this.getChildren().add(statusEffectsLayout);
    }


    public ImageView getSprite() {
        return this.sprite;
    }

    public Image getStillImage() {
        return this.spriteArray[2][0];
    }


    // // 0 = up, 1 = right, 2 = down, 3 = left
    public void updateImage(double time, int direction) {
        int index = (int) ((time % (2 * duration)) / duration); // will alternate between 0 and 1
        sprite.setImage(spriteArray[direction][index + 1]); // 1 and 2 are the walking animations so 1 is added
    }

    public CharacterUnit getUnit() {
        return this.unit;
    }


    // If active and not showing character menu, clicking on unit will open the menu
    // If non-active and left-click, toggle between displaying their movement range
    // If non-active and right-click, open their stats menu
    public void setSpriteHandlers() {
        sprite.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
