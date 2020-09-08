package main.model.combatSystem.abilities.personalAbilities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.IncreasedRange;
import main.model.combatSystem.statusEffects.Root;
import main.model.graphics.menus.AbilityPreview;

public class TowerAbility extends Ability {

    public TowerAbility() {
        super("Tower", 20, 0, 1,
                "Root yourself increasing the range of magical abilities");
    }



    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Root(activeUnit, activeUnit.getCharacterStatSheet().getMovement(), 3);
        new IncreasedRange(activeUnit, 4, 3);
        activeUnit.setActionToken(0);
        activeUnit.setMovementToken(false);
        return true;
    }


    @Override
    protected boolean targetsSelf() {
        return true;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public String getEffectType() {
        return "Status";
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        ImageView imageView = new ImageView(IncreasedRange.getStaticIcon());
        AbilityPreview.fitImageViewToAbilityView(imageView);
        return imageView;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }


    @Override
    public boolean targetsAlly() {
        return true;
    }
}
