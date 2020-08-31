package main.model.jobSystem.jobs.gunnerJob.gunnerAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.Blind;
import main.model.combatSystem.targetTypes.RingTarget;

public class SmokeAbility extends StatusEffectAbility {
    public SmokeAbility() {
        super("Smoke Screen", 10, 4, 2, 2, 20,
                "Blind an enemy with smoke");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Blind(receivingUnit, this.potency, this.duration);
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
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
    protected void setTargetType() {
        this.targetType = new RingTarget();
    }
}
