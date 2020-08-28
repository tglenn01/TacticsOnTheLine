package main.model.jobSystem.jobs.archerJob.archerAbilities;

import main.model.combatSystem.abilities.MagicAbility;

public class FlamingArrowAbility extends MagicAbility {
    public FlamingArrowAbility() {
        super("Flame Arrow", 2, 6, 1, 8, .95,
                "Accurately attack an enemy from afar using a magical arrow");
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
