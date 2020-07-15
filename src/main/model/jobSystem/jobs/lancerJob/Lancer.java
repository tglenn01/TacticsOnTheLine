package main.model.jobSystem.jobs.lancerJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.lancerJob.lancerAbilities.LungeAbility;
import main.model.jobSystem.jobs.lancerJob.lancerAbilities.OverwhelmAbility;
import main.model.jobSystem.jobs.lancerJob.lancerAbilities.WeakpointAbility;

public class Lancer extends Job {

    public Lancer() { this.jobTitle = "Lancer"; }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability lunge = new LungeAbility();
        Ability overwhelm = new OverwhelmAbility();
        Ability weakPoint = new WeakpointAbility();
        jobAbilityList.add(lunge);
        jobAbilityList.add(overwhelm);
        jobAbilityList.add(weakPoint);
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
        this.attackRange = 2;
    }
}
