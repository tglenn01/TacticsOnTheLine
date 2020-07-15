package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class IncreasedRange extends DecayingStatusEffect {


    public IncreasedRange(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setAbilityType() {
        this.abilityType = Ability.AbilityType.RANGE_INCREASE;
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "RNG_UP";
    }

    @Override
    protected void setIcon() {
        this.icon = new ImageView(new Image("resources/statusEffects/RangeUp.png"));
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass() == MagicAbility.class || ability.getClass() == StatusEffectAbility.class) {
                ability.setRange(ability.getRange() + potency); // oldRange + increasedRange
            }
        }
        this.amountChanged = potency;
    }

    @Override
    protected void removeStatusEffect(CharacterUnit receivingUnit) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass() == MagicAbility.class || ability.getClass() == StatusEffectAbility.class) {
                ability.setRange(ability.getRange() - amountChanged);
            }
        }
    }
}
