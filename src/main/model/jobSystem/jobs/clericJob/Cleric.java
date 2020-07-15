package main.model.jobSystem.jobs.clericJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.clericJob.clericAbilities.BlessAbility;
import main.model.jobSystem.jobs.clericJob.clericAbilities.HolyAbility;
import main.model.jobSystem.jobs.clericJob.clericAbilities.ProtectAbility;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability bless = new BlessAbility();
        Ability protect = new ProtectAbility();
        Ability holy = new HolyAbility();
        jobAbilityList.add(bless);
        jobAbilityList.add(protect);
        jobAbilityList.add(holy);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 34;
        this.jobMana = 30;
        this.jobStrength = 6;
        this.jobMagic = 10;
        this.jobArmour = 4;
        this.jobResistance = 12;
        this.jobSpeed = 4;
        this.jobDexterity = 6;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 2;
    }
}
