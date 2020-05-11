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
                15, .80, "Strike an enemy with increased force");
        Ability daggerThrow = new PhysicalAbility("Dagger Throw", 3, 3, 1,
                8, .70, "Strike an nearby enemy with an light dagger");
        Ability desperation = new PhysicalAbility("Desperation", 10, 1, 2,
                30, .50, "Spend a lot of mana for a low chance to deal a lot " +
                "of damage to multiple neighbouring enemies");
        jobAbilityList.add(strike);
        jobAbilityList.add(daggerThrow);
        jobAbilityList.add(desperation);
    }
}
