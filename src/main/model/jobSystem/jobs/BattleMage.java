package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class BattleMage extends Job {

    public BattleMage() {
        jobTitle = "BattleMage";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability zap = new MagicAbility("Zap", 2, 4, 16,
                1, .90, "deal lightning damage to an enemy from afar");
        Ability scorch = new MagicAbility("Scorch", 6, 3, 10,
                2, .70, "deal light fire damage to multiple enemies from afar");
        Ability cripple = new StatusEffectAbility("Cripple", 6, 2, 1,
                StatusEffectAbility.StatusType.ATTACK_DEBUFF, "Lower a nearby enemies attack");
        jobAbilityList.add(zap);
        jobAbilityList.add(scorch);
        jobAbilityList.add(cripple);
    }
}
