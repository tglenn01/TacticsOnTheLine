package main.model.combatSystem.abilities.personalAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.abilities.DamageAbility;
import main.model.combatSystem.statusEffects.Root;

public class TremorAbility extends DamageAbility {


    public TremorAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getStrength() + this.damage) - receivingUnit.getCharacterStatSheet().getArmour();
        if (damage < 0) damage = 0;
        else rootUnit(receivingUnit);
        return damage;
    }

    private void rootUnit(CharacterUnit receivingUnit) {
        DecayingStatusEffect root = new Root(receivingUnit, receivingUnit.getCharacterStatSheet().getMovement(), 2);
        receivingUnit.getStatusEffects().addDecayingStatusEffect(root);
    }
}
