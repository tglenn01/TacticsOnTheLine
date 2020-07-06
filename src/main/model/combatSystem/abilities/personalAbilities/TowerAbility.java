package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.statusEffects.IncreasedRange;
import main.model.combatSystem.statusEffects.Root;

public class TowerAbility extends Ability {

    public TowerAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        DecayingStatusEffect root = new Root(receivingUnit, activeUnit.getCharacterStatSheet().getMovement(), 3);
        DecayingStatusEffect doubleRange = new IncreasedRange(activeUnit, 4, 3);
        activeUnit.getStatusEffects().addDecayingStatusEffect(root);
        activeUnit.getStatusEffects().addDecayingStatusEffect(doubleRange);
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }
}
