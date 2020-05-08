package main.model.combatSystem.abilities;

import main.model.combatSystem.Ability;

public class StatusEffectAbility extends Ability {
    public enum StatusType {HEAL, ATTACK_BUFF, DEFENSE_BUFF, ATTACK_DEBUFF, DEFENSE_DEBUFF}
    private StatusType statusType;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, StatusType statusType) {
        super(abilityName, manaCost, range, areaOfEffect);
        this.statusType = statusType;
    }



}
