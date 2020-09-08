package main.model.jobSystem.jobs.bardJob.bardAbilities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseBuff;
import main.model.combatSystem.statusEffects.DefenseDebuff;
import main.model.graphics.menus.AbilityPreview;

public class SerenadeAbility extends StatusEffectAbility {
    public SerenadeAbility() {
        super("Serenade", 10, 4, 2, 3, 2,
                "Serenade your allies, buffing their defense");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new DefenseBuff(receivingUnit, this.potency, this.duration);
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
        return true;
    }

    @Override
    public String getEffectType() {
        return "Status";
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        ImageView imageView = new ImageView(DefenseDebuff.getStaticIcon());
        AbilityPreview.fitImageViewToAbilityView(imageView);
        return imageView;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }
}
