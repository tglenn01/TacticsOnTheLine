package main.model.jobSystem.jobs.battleMageJob.battleMageAbilities;

import main.model.combatSystem.abilities.MagicAbility;

public class ZapAbility extends MagicAbility {
    public ZapAbility() {
        super("Zap", 2, 4, 1, 16, .90,
                "deal lightning damage to an enemy from afar");
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
