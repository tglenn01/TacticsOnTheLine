package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.statusEffects.Invulnerable;
import main.model.itemSystem.StatusEffectConsumable;

public class GlassShieldConsumable extends StatusEffectConsumable {

    // duration is the uses
    public GlassShieldConsumable() {
        super("Glass Shield", 1,
                "Ally becomes invulnerable to their next attack", 1);
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        new Invulnerable(receivingUnit, this.duration);
    }
}
