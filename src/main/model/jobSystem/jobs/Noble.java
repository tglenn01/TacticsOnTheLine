package main.model.jobSystem.jobs;

import main.model.battleSystem.Ability;
import main.model.jobSystem.Job;

// default/base class
public class Noble extends Job {

    public Noble() {
        jobTitle = "Noble";
    }

    @Override
    protected void initializeAbilities() {
        Ability strike = new Ability("Strike", 15, 3, .80,
                1, 1);
        Ability daggerThrow = new Ability("Dagger Throw", 8, 3, .70,
                3, 1);
        Ability desperation = new Ability("Desperation", 30, 10, .50,
                1, 2);
        jobAbilityList.add(strike);
        jobAbilityList.add(daggerThrow);
        jobAbilityList.add(desperation);
    }
}
