package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;

public class StatusEffectAbility extends Ability {

    public enum StatusType {HEAL, ATTACK_BUFF, DEFENSE_BUFF, ATTACK_DEBUFF, DEFENSE_DEBUFF}
    private StatusType statusType;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, StatusType statusType) {
        super(abilityName, manaCost, range, areaOfEffect);
        this.statusType = statusType;
    }


    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit reciveingUnit)
            throws OutOfManaException, AttackMissedException, UnitIsDeadException {

    }
}
