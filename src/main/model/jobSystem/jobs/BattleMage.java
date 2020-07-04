package main.model.jobSystem.jobs;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class BattleMage extends Job {

    public BattleMage() {
        jobTitle = "BattleMage";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability zap = new MagicAbility("Zap", 2, 4, 1,
                Ability.AbilityType.DAMAGE,16, .90,
                "deal lightning damage to an enemy from afar");
        Ability scorch = new MagicAbility("Scorch", 6, 3, 2,
                Ability.AbilityType.DAMAGE,10, .70,
                "deal light fire damage to multiple enemies from afar");
        Ability cripple = new StatusEffectAbility("Cripple", 6, 2, 1,
                3, Ability.AbilityType.ATTACK_DEBUFF, 6, "Lower a nearby enemies attack");
        jobAbilityList.add(zap);
        jobAbilityList.add(scorch);
        jobAbilityList.add(cripple);
    }

    @Override
    protected void initializeJobStats() {
        this.jobHealth = 20;
        this.jobMana = 36;
        this.jobStrength = 6;
        this.jobMagic = 12;
        this.jobArmour = 2;
        this.jobResistance = 8;
        this.jobSpeed = 4;
        this.jobDexterity = 2;
    }
}
