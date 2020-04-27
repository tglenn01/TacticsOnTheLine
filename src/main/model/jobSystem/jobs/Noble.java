package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobAbilities.Ability;
import main.model.jobSystem.jobAbilities.PhysicalAbility;

// default/base class
public class Noble extends Job {

    public Noble() {
        jobTitle = "Noble";
    }

    @Override
    protected void initializeAbilities() {
        Ability strike = new PhysicalAbility("Strike", 15, 3, .80,
                1, 1);
        Ability daggerThrow = new PhysicalAbility("Dagger Throw", 8, 3, .70,
                3, 1);
        Ability desperation = new PhysicalAbility("Desperation", 30, 10, .50,
                1, 2);
        jobAbilityList.add(strike);
        jobAbilityList.add(daggerThrow);
        jobAbilityList.add(desperation);
    }
}
