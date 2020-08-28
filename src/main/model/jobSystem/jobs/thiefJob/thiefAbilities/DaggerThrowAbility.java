package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class DaggerThrowAbility extends PhysicalAbility {
    public DaggerThrowAbility() {
        super("Dagger Throw", 4, 8, 1, 10, .70,
                "Throw a dagger at an enemy from afar");
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
