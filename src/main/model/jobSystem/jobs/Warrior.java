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
                Ability.AbilityType.DAMAGE,18, .70,
                "Club a neighbouring enemy on the head with low accuracy");
        Ability powerUp = new StatusEffectAbility("Power UP!", 4, 1, 1,
                Ability.AbilityType.ATTACK_BUFF, "Buff the attack of a neighbouring ally");
        Ability stone = new PhysicalAbility("Stone", 1, 4, 1,
                Ability.AbilityType.DAMAGE,8, .60,
                "A low chance to damage an enemy from afar");
        jobAbilityList.add(club);
        jobAbilityList.add(powerUp);
        jobAbilityList.add(stone);
    }
}
