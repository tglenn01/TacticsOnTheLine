package main.model.itemSystem.items;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.SupportiveAbility;
import main.model.itemSystem.Consumable;

public class ManaPotionConsumable extends Consumable {

    public ManaPotionConsumable() {
        super("Mana Potion", 15);
    }

    @Override
    public void applyItem(CharacterUnit receivingUnit) {
        SupportiveAbility.gainMana(receivingUnit, receivingUnit.getCharacterStatSheet(), this.potency);
    }
}
