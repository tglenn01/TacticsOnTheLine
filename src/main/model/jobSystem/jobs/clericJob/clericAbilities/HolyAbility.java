package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.model.combatSystem.abilities.MagicAbility;

public class HolyAbility extends MagicAbility {

    public HolyAbility() {
        super("Holy", 10, 3, 1, 20, .80,
                "Deal heavy light damage to a nearby enemy");
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
