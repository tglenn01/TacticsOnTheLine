package main.model.combatSystem.abilities.personalAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.IncreasedRange;
import main.model.combatSystem.statusEffects.Root;

public class TowerAbility extends Ability {

    public TowerAbility() {
        super("Tower", 20, 0, 1,
                "Root yourself increasing the range of magical abilities");
    }

    @Override
    public String getEffectType() {
        return "Support";
    }

    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Root(activeUnit, activeUnit.getCharacterStatSheet().getMovement(), 3);
        new IncreasedRange(activeUnit, 4, 3);
        activeUnit.setActionToken(0);
        activeUnit.setMovementToken(false);
        return true;
    }


    @Override
    protected boolean targetsSelf() {
        return true;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }
}
