package main.model.jobSystem.jobs;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Bard extends Job {

    public Bard() {
        this.jobTitle = "Bard";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability sing = new StatusEffectAbility("Sing", 10, 4, 2, 1,
                Ability.AbilityType.HEAL, 2, "Calm your allies nerves with a song, healing them");
        Ability serenade = new StatusEffectAbility("Serenade", 10, 4, 2,
                3, Ability.AbilityType.DEFENSE_BUFF, 2,
                "Serenade your allies, buffing their defense");
        Ability inspire = new StatusEffectAbility("Inspire", 10, 4, 2,
                3, Ability.AbilityType.ATTACK_BUFF, 2,
                "Inspire your allies, boosting their attack");
        jobAbilityList.add(sing);
        jobAbilityList.add(serenade);
        jobAbilityList.add(inspire);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 16;
        this.jobMana = 40;
        this.jobStrength = 2;
        this.jobMagic = 6;
        this.jobArmour = 2;
        this.jobResistance = 8;
        this.jobSpeed = 6;
        this.jobDexterity = 2;
    }
}
