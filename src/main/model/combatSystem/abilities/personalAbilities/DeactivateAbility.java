package main.model.combatSystem.abilities.personalAbilities;

import javafx.scene.Node;
import javafx.scene.control.Label;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.SupportiveAbility;

public class DeactivateAbility extends SupportiveAbility {

    public DeactivateAbility() {
        super("Deactivate", 0, 0, 1,
                "Pass your turn to heal to full");
    }

    @Override
    public String getEffectType() {
        return "Heal";
    }

    @Override
    // receivingUnit and activeUnit are the same in this ability
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        healUnit(receivingUnit, receivingUnit.getCharacterStatSheet(), activeUnit.getCharacterStatSheet().getMaxHealth());

        activeUnit.setMovementToken(false);
        activeUnit.setActionToken(0);
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
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return new Label(Integer.toString(activeUnit.getCharacterStatSheet().getMaxHealth()));
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }


    @Override
    public boolean targetsAlly() {
        return false;
    }
}
