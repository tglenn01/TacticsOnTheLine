package main.model.jobSystem.jobs.warriorJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.warriorJob.warriorAbilities.ClubAbility;
import main.model.jobSystem.jobs.warriorJob.warriorAbilities.FocusAbility;
import main.model.jobSystem.jobs.warriorJob.warriorAbilities.StoneAbility;

public class Warrior extends Job {

    public Warrior() {
        jobTitle = "Warrior";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability club = new ClubAbility();
        Ability focus = new FocusAbility();
        Ability stone = new StoneAbility();
        jobAbilityList.add(club);
        jobAbilityList.add(focus);
        jobAbilityList.add(stone);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 40;
        this.jobMana = 8;
        this.jobStrength = 13;
        this.jobMagic = 2;
        this.jobArmour = 8;
        this.jobResistance = 0;
        this.jobSpeed = 6;
        this.jobDexterity = 6;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 1;
    }
}
