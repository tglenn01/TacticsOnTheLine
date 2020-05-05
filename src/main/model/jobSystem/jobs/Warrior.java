package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.jobSystem.jobAbilities.Ability;
import main.model.jobSystem.jobAbilities.PhysicalAbility;
import main.model.jobSystem.jobAbilities.StatusEffectAbility;

public class Warrior extends Job {

    public Warrior() {
        jobTitle = "Warrior";
    }

    @Override
    protected void initializeAbilities() {
        Ability club = new PhysicalAbility("Club", 2, 1, 1,
                18, .90);
        Ability powerUp = new StatusEffectAbility("Power UP!", 4, 1, 1,
                StatusEffectAbility.StatusType.ATTACK_BUFF);
        Ability stone = new PhysicalAbility("Stone", 1, 4, 1,
                8, .60);
        jobAbilityList.add(club);
        jobAbilityList.add(powerUp);
        jobAbilityList.add(stone);
    }
}
