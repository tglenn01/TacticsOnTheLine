package main.model.jobSystem.jobs.nobleJob.nobleAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class StrikeAbility extends PhysicalAbility {
    public StrikeAbility() {
        super("Strike", 3, 1, 1, 15, .80,
                "Strike an enemy with increased force");
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
