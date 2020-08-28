package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;

public class ClubAbility extends PhysicalAbility {
    public ClubAbility() {
        super("Club", 2, 1, 1, 18, .70,
                "Club a neighbouring enemy on the head with low accuracy");
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
