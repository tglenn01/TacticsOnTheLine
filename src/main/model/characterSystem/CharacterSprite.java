package main.model.characterSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.graphics.menus.AbilityMenu;
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
            sprite.setOnMouseClicked(e -> {
                if (TacticBaseBattle.getInstance().getBattle().getActiveCharacter() == unit) {
                    if (!AbilityMenu.isDisplaying()) {
                        AbilityMenu.display(unit, unit.getCharacterJob().getJobAbilityList());
                    }
                } else if (unit.isMovementRangeIsVisable()) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(unit);
                    unit.setMovementRangeIsVisable(false);
                    e.consume();
                } else {
                    TacticBaseBattle.getInstance().getCurrentBoard().displayValidMovementSpaces(unit, unit.getCharacterStatSheet().getMovement());
                    unit.setMovementRangeIsVisable(true);
                    e.consume();
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
