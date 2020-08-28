package main.model.jobSystem.jobs.gunnerJob.gunnerAbilities;

import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.targetTypes.CrossTarget;

public class BuckShotAbility extends PhysicalAbility {

    public BuckShotAbility() {
        super("Buckshot", 6, 4, 1, 20, .85,
                "Fire a cluster exploding on enemies in a unique patter");
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

    @Override
    protected void setTargetType() {
        this.targetType = new CrossTarget();
    }
}
