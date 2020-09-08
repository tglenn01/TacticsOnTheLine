package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.Blind;
import main.model.graphics.menus.AbilityPreview;

public class BlindAbility extends StatusEffectAbility {
    public BlindAbility() {
        super("Blind", 10, 1, 2, 3, 15,
                "Blind enemies lowering their attack potency");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Blind(receivingUnit, this.potency, this.duration);
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }

    @Override
    public String getEffectType() {
        return "Status";
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        ImageView imageView = new ImageView(Blind.getStaticIcon());
        AbilityPreview.fitImageViewToAbilityView(imageView);
        return imageView;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }
}
