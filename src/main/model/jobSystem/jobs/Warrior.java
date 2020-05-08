package main.model.jobSystem.jobs;

import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Warrior extends Job {

    public Warrior() {
        jobTitle = "Warrior";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
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
