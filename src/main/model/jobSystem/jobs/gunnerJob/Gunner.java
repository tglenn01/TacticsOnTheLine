package main.model.jobSystem.jobs.gunnerJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.jobs.gunnerJob.gunnerAbilities.BuckShotAbility;
import main.model.jobSystem.Job;

public class Gunner extends Job {

    public Gunner() { this.jobTitle = "Gunner"; }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability buckshot = new BuckShotAbility();
        jobAbilityList.add(buckshot);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 26;
        this.jobMana = 30;
        this.jobStrength = 14;
        this.jobMagic = 2;
        this.jobArmour = 8;
        this.jobResistance = 3;
        this.jobSpeed = 10;
        this.jobDexterity = 10;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 4;
    }
}
