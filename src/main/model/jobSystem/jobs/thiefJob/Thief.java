package main.model.jobSystem.jobs.thiefJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.thiefJob.thiefAbilities.BlindAbility;
import main.model.jobSystem.jobs.thiefJob.thiefAbilities.CrossSlashAbility;
import main.model.jobSystem.jobs.thiefJob.thiefAbilities.DaggerThrowAbility;

public class Thief extends Job {

    public Thief() {
        jobTitle = "Thief";
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability crossSlash = new CrossSlashAbility();
        Ability blind = new BlindAbility();
        Ability daggerThrow = new DaggerThrowAbility();
        jobAbilityList.add(crossSlash);
        jobAbilityList.add(blind);
        jobAbilityList.add(daggerThrow);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 20;
        this.jobMana = 16;
        this.jobStrength = 10;
        this.jobMagic = 10;
        this.jobArmour = 2;
        this.jobResistance = 2;
        this.jobSpeed = 16;
        this.jobDexterity = 18;
        this.jobMovement = StatSheet.BASE_MOVEMENT + 1;
        this.attackRange = 1;
    }
}
