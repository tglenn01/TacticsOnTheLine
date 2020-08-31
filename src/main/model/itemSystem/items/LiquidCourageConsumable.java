package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.statusEffects.AttackBuff;
import main.model.itemSystem.StatusEffectConsumable;

public class LiquidCourageConsumable extends StatusEffectConsumable {

    public LiquidCourageConsumable() {
        super("Liquid Courage", 5, "Buff an allies attack", 3);
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        new AttackBuff(receivingUnit, this.potency, this.duration);
    }
}
