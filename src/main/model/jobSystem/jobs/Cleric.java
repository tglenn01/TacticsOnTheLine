package main.model.jobSystem.jobs;

import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability heal = new StatusEffectAbility("Heal", 4, 1, 1, 1,
                Ability.AbilityType.HEAL, 6, "Heal a neighbouring ally");
        Ability protect = new StatusEffectAbility("Protect", 8, 1, 1,
                3, Ability.AbilityType.DEFENSE_BUFF, 4, "Buff a neighbouring allies defense");
        Ability holy = new MagicAbility("Holy", 10, 3, 1,
                Ability.AbilityType.DAMAGE, 20, .80,
                "Deal heavy light damage to a nearby enemy");
        jobAbilityList.add(heal);
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
    }
}
