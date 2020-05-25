package main.model.jobSystem.jobs;

import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.PhysicalAbility;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.jobSystem.Job;

public class Lancer extends Job {

    public Lancer() { this.jobTitle = "Lancer"; }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        Ability lunge = new PhysicalAbility("Lunge", 4, 2, 1,
                Ability.AbilityType.DAMAGE,12, .95,
                "Strike a nearby enemy with a powerful lunge");
        Ability overwhelm = new StatusEffectAbility("Overwhelm", 20, 2, 4,
                3, Ability.AbilityType.DEFENSE_DEBUFF,
                "Scare all neighbouring enemies, weakening their defense");
        Ability weakPoint = new PhysicalAbility("Weakpoint", 6, 1, 1,
                Ability.AbilityType.DAMAGE,16, .80,
                "Strike an Enemies weakpoint with the blunt of your spear");
        jobAbilityList.add(lunge);
        jobAbilityList.add(overwhelm);
        jobAbilityList.add(weakPoint);
    }

    @Override
    public void setBaseStats(StatSheet statSheet) {
        statSheet.setMaxHealth(26);
        statSheet.setMaxMana(30);
        statSheet.setBaseStrength(14);
        statSheet.setBaseMagic(2);
        statSheet.setBaseArmour(8);
        statSheet.setBaseResistance(3);
        statSheet.setBaseSpeed(10);
        statSheet.setBaseDexterity(10);
        statSheet.setMovement(StatSheet.BASE_MOVEMENT);
    }
}
