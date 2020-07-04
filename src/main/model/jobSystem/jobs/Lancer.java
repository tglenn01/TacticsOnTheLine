package main.model.jobSystem.jobs;

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
        Ability overwhelm = new StatusEffectAbility("Overwhelm", 20, 2, 2,
                3, Ability.AbilityType.DEFENSE_DEBUFF, 2,
                "Scare all neighbouring enemies, weakening their defense");
        Ability weakPoint = new PhysicalAbility("Weakpoint", 6, 1, 1,
                Ability.AbilityType.DAMAGE,16, .80,
                "Strike an Enemies weakpoint with the blunt of your spear");
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
    }
}
