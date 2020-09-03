package main.model.jobSystem.jobs.archerJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.archerJob.archerAbilities.FlurryAbility;
import main.model.jobSystem.jobs.archerJob.archerAbilities.HinderAbility;
import main.model.jobSystem.jobs.archerJob.archerAbilities.FlamingArrowAbility;

public class Archer extends Job {

    public Archer() {
        jobTitle = "Archer";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability snipe = new FlamingArrowAbility();
        Ability hinder = new HinderAbility();
        Ability flurry = new FlurryAbility();
        jobAbilityList.add(snipe);
        jobAbilityList.add(hinder);
        jobAbilityList.add(flurry);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 34;
        this.jobMana = 30;
        this.jobStrength = 100;
        this.jobMagic = 8;
        this.jobArmour = 4;
        this.jobResistance = 4;
        this.jobSpeed = 120;
        this.jobDexterity = 12;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 4;
    }
}
