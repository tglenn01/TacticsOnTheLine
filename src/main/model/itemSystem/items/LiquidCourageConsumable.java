package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.statusEffects.AttackBuff;
import main.model.itemSystem.StatusEffectConsumable;

public class LiquidCourageConsumable extends StatusEffectConsumable {

    public LiquidCourageConsumable() {
        super("Liquid Courage", 5, 3);
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        DecayingStatusEffect attackBuff = new AttackBuff(receivingUnit, this.potency, this.duration);
        receivingUnit.getStatusEffects().addDecayingStatusEffect(attackBuff);
    }
}
