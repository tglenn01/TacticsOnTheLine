package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class StoneAbility extends PhysicalAbility {
    public StoneAbility() {
        super("Stone", 1, 4, 1, 8, .60,
                "A low chance to damage an enemy from afar");
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
