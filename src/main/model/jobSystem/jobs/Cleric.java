package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.jobSystem.Job;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.MagicAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class Cleric extends Job {

    public Cleric() {
        jobTitle = "Cleric";
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability heal = new StatusEffectAbility("Heal", 4, 1, 1,
                Ability.AbilityType.HEAL, "Heal a neighbouring ally");
        Ability protect = new StatusEffectAbility("Protect", 8, 1, 1,
                Ability.AbilityType.DEFENSE_BUFF, "Buff a neighbouring allies defense");
        Ability holy = new MagicAbility("Holy", 10, 3, 1,
                Ability.AbilityType.DAMAGE, 20, .80,
                "Deal heavy light damage to a nearby enemy");
        jobAbilityList.add(heal);
        jobAbilityList.add(protect);
        jobAbilityList.add(holy);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        int initialHealth = 30;
        statSheet.setHealth(initialHealth);
        statSheet.setMaxHealth(initialHealth);
        statSheet.setMana(30);
        statSheet.setStrength(4);
        statSheet.setMagic(12);
        statSheet.setArmour(4);
        statSheet.setResistance(12);
        statSheet.setSpeed(4);
        statSheet.setDexterity(6);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
