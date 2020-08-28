package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class LungeAbility extends PhysicalAbility {
    public LungeAbility() {
        super("Lunge", 4, 2, 1,12, .95,
                "Strike a nearby enemy with a powerful lunge");
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
