package main.model.jobSystem.jobs.bardJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.bardJob.bardAbilities.InspireAbility;
import main.model.jobSystem.jobs.bardJob.bardAbilities.SerenadeAbility;
import main.model.jobSystem.jobs.bardJob.bardAbilities.SingAbility;

public class Bard extends Job {

    public Bard() {
        this.jobTitle = "Bard";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability sing = new SingAbility();
        Ability serenade = new SerenadeAbility();
        Ability inspire = new InspireAbility();
        jobAbilityList.add(sing);
        jobAbilityList.add(serenade);
        jobAbilityList.add(inspire);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 26;
        this.jobMana = 40;
        this.jobStrength = 2;
        this.jobMagic = 6;
        this.jobArmour = 2;
        this.jobResistance = 8;
        this.jobSpeed = 6;
        this.jobDexterity = 2;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 3;
    }
}
