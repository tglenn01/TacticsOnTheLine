package main.model.jobSystem.jobs.battleMageJob.battleMageAbilities;

import main.model.combatSystem.abilities.MagicAbility;

public class ScorchAbility extends MagicAbility {
    public ScorchAbility() {
        super("Scorch", 6, 3, 2,10, .70,
                "deal light fire damage to multiple enemies from afar");
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
