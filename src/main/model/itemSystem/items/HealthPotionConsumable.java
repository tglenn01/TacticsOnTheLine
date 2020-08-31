package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.SupportiveAbility;
import main.model.itemSystem.Consumable;

public class HealthPotionConsumable extends Consumable {

    public HealthPotionConsumable() {
        super("Health Potion", 20, "Heal an ally");
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        SupportiveAbility.healUnit(receivingUnit, receivingUnit.getCharacterStatSheet(), this.potency);
    }
}
