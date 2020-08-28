package main.model.jobSystem;

import main.model.combatSystem.abilities.PhysicalAbility;

public class BasicAttackAbility extends PhysicalAbility {
    public BasicAttackAbility(int range) {
        super("Attack", 0, range, 1, 0, .90,
                "Attack an enemy");
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
