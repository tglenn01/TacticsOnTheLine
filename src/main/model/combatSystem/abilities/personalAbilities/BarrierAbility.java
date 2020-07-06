package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.PermanentStatusEffect;
import main.model.combatSystem.statusEffects.Invulnerable;

public class BarrierAbility extends Ability {

    public BarrierAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        PermanentStatusEffect invulnerable = new Invulnerable(receivingUnit, 1);
        receivingUnit.getStatusEffects().addPermanentStatusEffect(invulnerable);
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }
}
