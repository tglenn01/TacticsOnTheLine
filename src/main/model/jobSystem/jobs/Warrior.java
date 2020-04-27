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
        Ability club = new PhysicalAbility("Club", 18, 2, .70,
                1, 1);
        Ability powerUp = new StatusEffectAbility("Power UP!", 4, 1, 1,
                StatusEffectAbility.StatusType.ATTACK_BUFF);
        Ability stone = new PhysicalAbility("Stone", 6, 1, .60,
                4, 1);
        jobAbilityList.add(club);
        jobAbilityList.add(powerUp);
        jobAbilityList.add(stone);
    }
}
