package main.model.graphics.sceneElements.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.menus.AbilityMenu;
import main.model.graphics.menus.CharacterStatsMenu;
import main.ui.TacticBaseBattle;

import java.io.FileInputStream;

public class CharacterSprite {
    public static final String ESTELLE_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\estelleSprite.png";
    public static final String JOSHUA_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\joshuaSprite.png";
    public static final String KLOE_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\kloeSprite.png";
    public static final String CASSIUS_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\cassiusSprite.png";
    public static final String ENEMY_SPRITE = "D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\enemySprite.png";
    private CharacterUnit unit;
    private ImageView sprite;
    private Image image;

    public CharacterSprite(CharacterUnit unit, String fileLocation) {
        this.unit = unit;
        initializeSprite(fileLocation);
    }

    private void initializeSprite(String fileLocation) {
        try {
            FileInputStream input = new FileInputStream(fileLocation);
            this.image = new Image(input);
            this.sprite = new ImageView(image);
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
        } catch (Exception e) {
            System.out.println("Portrait not found");
        }
    }

    public ImageView getSprite() {
        return this.sprite;
    }

    public Image getImage() {
        return this.image;
    }
}
