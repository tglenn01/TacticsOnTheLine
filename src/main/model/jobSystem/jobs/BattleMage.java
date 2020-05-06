package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobAbilities.Ability;
import main.model.jobSystem.jobAbilities.MagicAbility;
import main.model.jobSystem.jobAbilities.StatusEffectAbility;

public class BattleMage extends Job {

    public BattleMage() {
        jobTitle = "BattleMage";
    }

    @Override
    protected void initializeAbilities() {
        Ability zap = new MagicAbility("Zap", 2, 4, 16,
                1, .90);
        Ability scorch = new MagicAbility("Scorch", 6, 3, 10,
                2, .70);
        Ability cripple = new StatusEffectAbility("Cripple", 6, 2, 1,
                StatusEffectAbility.StatusType.ATTACK_DEBUFF);
        jobAbilityList.add(zap);
        jobAbilityList.add(scorch);
        jobAbilityList.add(cripple);
    }
}
