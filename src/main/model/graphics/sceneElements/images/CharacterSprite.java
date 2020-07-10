package main.model.graphics.sceneElements.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.menus.AbilityMenu;
import main.model.graphics.menus.CharacterStatsMenu;
import main.ui.TacticBaseBattle;

public abstract class CharacterSprite {
    private CharacterUnit unit;
    protected Image[][] spriteArray = new Image[4][3]; // 4 directions and 3 different animations per direction
    private ImageView sprite;
    private Image image;
    public double duration = 1.00;

    protected int direction = 0;

    public CharacterSprite(CharacterUnit unit) {
        this.unit = unit;
        initializeSpriteArray();
        initializeSprite();
    }

    protected abstract void initializeSpriteArray();

    private void initializeSprite() {
        this.sprite = new ImageView(spriteArray[2][0]); // default down looking
        sprite.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (TacticBaseBattle.getInstance().getBattle().getActiveCharacter() == unit &&
                    !TacticBaseBattle.getInstance().getCurrentBoard().isAbilitySpacesShowing()) {
                if (!AbilityMenu.isDisplaying()) {
                    AbilityMenu.display(unit, unit.getAbilityList());
                }
            } else if (!TacticBaseBattle.getInstance().getCurrentBoard().isAbilitySpacesShowing() &&
                    event.getButton() == MouseButton.PRIMARY) {
                if (unit.isMovementRangeIsVisable()) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(unit);
                    unit.setMovementRangeIsVisable(false);
                } else {
                    TacticBaseBattle.getInstance().getCurrentBoard().displayValidMovementSpaces(unit, unit.getCharacterStatSheet().getMovement());
                    unit.setMovementRangeIsVisable(true);
                }
                event.consume();
            } else if (!TacticBaseBattle.getInstance().getCurrentBoard().isAbilitySpacesShowing() &&
                    event.getButton() == MouseButton.SECONDARY) {
                if (!CharacterStatsMenu.isDisplaying()) CharacterStatsMenu.display(unit);
            }
        });
    }

    public ImageView getSprite() {
        return this.sprite;
    }

    public Image getImage(int direction) {
        return this.spriteArray[direction][0];
    }

    public void updateImage(double time, int direction) {
        int index = (int) ((time % (3 * duration)) / duration);
        this.sprite.setImage(spriteArray[direction][index]);
    }
}
