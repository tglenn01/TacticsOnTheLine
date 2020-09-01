package main.model.combatSystem.statusEffects;

import javafx.scene.image.Image;
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
    protected void setCondensedName() {
        this.condensedName = "RNG_UP";
    }

    @Override
    protected void setIcon() {
        this.icon = new Image("resources/statusEffects/RangeUp.png");
    }

    public static Image getStaticIcon() {
        return new Image("resources/statusEffects/RangeUp.png");
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass().getSuperclass() == MagicAbility.class || ability.getClass().getSuperclass() == StatusEffectAbility.class) {
                ability.setRange(ability.getRange() + potency); // oldRange + increasedRange
            }
        }
        this.amountChanged = potency;
    }

    @Override
    public void removeStatusEffect(CharacterUnit receivingUnit) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass().getSuperclass() == MagicAbility.class || ability.getClass().getSuperclass() == StatusEffectAbility.class) {
                ability.setRange(ability.getRange() - amountChanged);
            }
        }
    }
}
