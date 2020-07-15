package main.model.jobSystem.jobs.nobleJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.nobleJob.nobleAbilities.DaggerAbility;
import main.model.jobSystem.jobs.nobleJob.nobleAbilities.DesperationAbility;
import main.model.jobSystem.jobs.nobleJob.nobleAbilities.StrikeAbility;

// default/base class
public class Noble extends Job {

    public Noble() {
        jobTitle = "Noble";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability strike = new StrikeAbility();
        Ability dagger = new DaggerAbility();
        Ability desperation = new DesperationAbility();
        jobAbilityList.add(strike);
        jobAbilityList.add(dagger);
        jobAbilityList.add(desperation);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 32;
        this.jobMana = 15;
        this.jobStrength = 10;
        this.jobMagic = 4;
        this.jobArmour = 8;
        this.jobResistance = 2;
        this.jobSpeed = 8;
        this.jobDexterity = 8;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 1;
    }
}
