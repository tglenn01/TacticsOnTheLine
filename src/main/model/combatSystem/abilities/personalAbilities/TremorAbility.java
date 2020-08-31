package main.model.combatSystem.abilities.personalAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.DamageAbility;
import main.model.combatSystem.statusEffects.Root;
import main.model.combatSystem.targetTypes.SurroundingTarget;

public class TremorAbility extends DamageAbility {


    public TremorAbility() {
        super("Tremor", 4, 1, 2, 6, 1.00,
                "Damage neighbouring enemies while stopping their movement");
    }

    @Override
    public String getEffectType() {
        return "Physical";
    }

    @Override
    protected void setTargetType() {
        this.targetType = new SurroundingTarget();
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getStrength() + this.damage) - receivingUnit.getCharacterStatSheet().getArmour();
        if (damage < 0) damage = 0;
        rootUnit(receivingUnit);
        return damage;
    }

    private void rootUnit(CharacterUnit receivingUnit) {
        new Root(receivingUnit, receivingUnit.getCharacterStatSheet().getMovement(), 2);
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
