package main.model.combatSystem.abilities.personalAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.abilities.DamageAbility;
import main.model.combatSystem.statusEffects.Root;

public class TremorAbility extends DamageAbility {
    private CharacterUnit unitWithThisAbility;

    public TremorAbility(CharacterUnit unitWithThisAbility) {
        super("Tremor", 4, 1, 2, 6, 1.00,
                "Damage neighbouring enemies while stopping their movement");
        this.unitWithThisAbility = unitWithThisAbility;
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getStrength() + this.damage) - receivingUnit.getCharacterStatSheet().getArmour();
        if (damage < 0) damage = 0;
        rootUnit(receivingUnit);
        return damage;
    }

    private void rootUnit(CharacterUnit receivingUnit) {
        DecayingStatusEffect root = new Root(receivingUnit, receivingUnit.getCharacterStatSheet().getMovement(), 2);
        receivingUnit.getStatusEffects().addDecayingStatusEffect(root);
    }

    @Override
    protected boolean targetsSelf() {
        return true;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }
}
