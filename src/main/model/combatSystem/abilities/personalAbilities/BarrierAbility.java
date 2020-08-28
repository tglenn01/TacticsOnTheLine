package main.model.combatSystem.abilities.personalAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.PermanentStatusEffect;
import main.model.combatSystem.statusEffects.Invulnerable;

public class BarrierAbility extends Ability {

    public BarrierAbility() {
        super("Barrier", 14, 1, 1,
                "Protect an ally from damage one time");
    }

    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        PermanentStatusEffect invulnerable = new Invulnerable(receivingUnit, 1);
        receivingUnit.getStatusEffects().addPermanentStatusEffect(invulnerable);
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }
}
