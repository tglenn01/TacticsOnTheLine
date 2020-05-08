package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;

// default/base class
public class Noble extends Job {

    public Noble() {
        jobTitle = "Noble";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability strike = new PhysicalAbility("Strike", 3, 1, 1,
                15, .80);
        Ability daggerThrow = new PhysicalAbility("Dagger Throw", 3, 3, 1,
                8, .70);
        Ability desperation = new PhysicalAbility("Desperation", 10, 1, 2,
                30, .50);
        jobAbilityList.add(strike);
        jobAbilityList.add(daggerThrow);
        jobAbilityList.add(desperation);
    }
}
