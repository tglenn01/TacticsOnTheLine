package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class CrossSlashAbility extends PhysicalAbility {
    public CrossSlashAbility() {
        super("Cross-Slash", 2, 2, 1, 12, .95,
                "Slash an enemy with high accuracy");
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
