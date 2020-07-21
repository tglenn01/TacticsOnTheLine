package main.model.jobSystem.jobs.gunnerJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.jobs.gunnerJob.gunnerAbilities.BuckShotAbility;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.gunnerJob.gunnerAbilities.SmokeAbility;

public class Gunner extends Job {

    public Gunner() { this.jobTitle = "Gunner"; }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability buckshot = new BuckShotAbility();
        Ability smokeAbility = new SmokeAbility();
        jobAbilityList.add(buckshot);
        jobAbilityList.add(smokeAbility);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 36;
        this.jobMana = 30;
        this.jobStrength = 12;
        this.jobMagic = 0;
        this.jobArmour = 2;
        this.jobResistance = 2;
        this.jobSpeed = 12;
        this.jobDexterity = 14;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 4;
    }
}
