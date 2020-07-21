package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.PermanentStatusEffect;
import main.model.combatSystem.statusEffects.Invulnerable;
import main.model.itemSystem.StatusEffectConsumable;

public class GlassShieldConsumable extends StatusEffectConsumable {

    // duration is the uses
    public GlassShieldConsumable() {
        super("Glass Shield", 1, 1);
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        PermanentStatusEffect invulnerableEffect = new Invulnerable(receivingUnit, this.duration);
        receivingUnit.getStatusEffects().addPermanentStatusEffect(invulnerableEffect);
    }
}
