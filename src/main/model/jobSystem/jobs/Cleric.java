package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobAbilities.Ability;
import main.model.jobSystem.jobAbilities.MagicAbility;
import main.model.jobSystem.jobAbilities.StatusEffectAbility;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        Ability heal = new StatusEffectAbility("Heal", 4, 1, 1,
                StatusEffectAbility.StatusType.HEAL);
        Ability defenseUp = new StatusEffectAbility("Defense Up", 8, 1, 1,
                StatusEffectAbility.StatusType.DEFENSE_BUFF);
        Ability holy = new MagicAbility("Holy", 10, 3, 20,
                1, .80);
        jobAbilityList.add(heal);
        jobAbilityList.add(defenseUp);
        jobAbilityList.add(holy);
    }
}
