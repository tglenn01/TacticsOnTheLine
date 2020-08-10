package main.model.jobSystem.jobs.battleMageJob;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.battleMageJob.battleMageAbilities.CrippleAbility;
import main.model.jobSystem.jobs.battleMageJob.battleMageAbilities.ScorchAbility;
import main.model.jobSystem.jobs.battleMageJob.battleMageAbilities.ZapAbility;

public class BattleMage extends Job {

    public BattleMage() {
        jobTitle = "BattleMage";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability zap = new ZapAbility();
        Ability scorch = new ScorchAbility();
        Ability cripple = new CrippleAbility();
        jobAbilityList.add(zap);
        jobAbilityList.add(scorch);
        jobAbilityList.add(cripple);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 3;
        this.jobMana = 42;
        this.jobStrength = 6;
        this.jobMagic = 12;
        this.jobArmour = 2;
        this.jobResistance = 8;
        this.jobSpeed = 4;
        this.jobDexterity = 2;
        this.jobMovement = StatSheet.BASE_MOVEMENT;
        this.attackRange = 3;
    }
}
