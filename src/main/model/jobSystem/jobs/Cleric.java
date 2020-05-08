package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
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
