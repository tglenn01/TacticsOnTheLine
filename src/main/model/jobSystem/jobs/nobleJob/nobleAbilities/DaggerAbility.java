package main.model.jobSystem.jobs.nobleJob.nobleAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class DaggerAbility extends PhysicalAbility {
    public DaggerAbility() {
        super("Dagger", 3, 3, 1, 8, .70,
                "Strike an nearby enemy with an light dagger");
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
        return false;
    }
}
