package main.model.combatSystem.abilities.personalAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.abilities.DamageAbility;
import main.model.combatSystem.statusEffects.Root;

import java.util.List;

public class TremorAbility extends DamageAbility {

    public TremorAbility() {
        super("Tremor", 4, 0, 2,
                Ability.AbilityType.DAMAGE, 6, 1.00,
                "Damage neighbouring enemies while stopping their movement");
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

    @Override
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range);
    }
}
