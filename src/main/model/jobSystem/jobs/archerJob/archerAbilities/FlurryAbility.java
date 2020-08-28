package main.model.jobSystem.jobs.archerJob.archerAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class FlurryAbility extends PhysicalAbility {
    public FlurryAbility() {
        super("Flurry", 7, 6, 2, 8, .95,
                "Hit multiple enemies from afar for light damage");
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
}
