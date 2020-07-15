package main.model.jobSystem.jobs.archerJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.archerJob.archerAbilities.FlurryAbility;
import main.model.jobSystem.jobs.archerJob.archerAbilities.HinderAbility;
import main.model.jobSystem.jobs.archerJob.archerAbilities.SnipeAbility;

public class Archer extends Job {

    public Archer() {
        jobTitle = "Archer";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability snipe = new SnipeAbility();
        Ability hinder = new HinderAbility();
        Ability flurry = new FlurryAbility();
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(flurry);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 24;
        this.jobMana = 24;
        this.jobStrength = 10;
        this.jobMagic = 7;
        this.jobArmour = 4;
        this.jobResistance = 4;
        this.jobSpeed = 12;
        this.jobDexterity = 12;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 4;
    }
}
