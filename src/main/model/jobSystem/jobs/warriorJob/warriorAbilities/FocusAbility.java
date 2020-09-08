package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.AttackBuff;
import main.model.graphics.menus.AbilityPreview;

public class FocusAbility extends StatusEffectAbility {
    public FocusAbility() {
        super("Focus", 4, 1, 2, 1, 4,
                "Buff the attack of a neighbouring ally");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new AttackBuff(receivingUnit, this.potency, this.duration);
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
    public boolean targetsAlly() {
        return false;
    }

    @Override
    public String getEffectType() {
        return "Status";
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        ImageView imageView = new ImageView(AttackBuff.getStaticIcon());
        AbilityPreview.fitImageViewToAbilityView(imageView);
        return imageView;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }

}
