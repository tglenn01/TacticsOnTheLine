package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class WeakpointAbility extends PhysicalAbility {
    public WeakpointAbility() {
        super("Weakpoint", 6, 1, 1, 16, .80,
                "Strike an Enemies weak point with the blunt of your spear");
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
