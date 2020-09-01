package main.model.jobSystem.jobs.bardJob.bardAbilities;

import javafx.scene.Node;
import javafx.scene.control.Label;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class SingAbility extends StatusEffectAbility {
    public SingAbility() {
        super("Sing", 10, 4, 2, 1, 4,
                "Calm your allies nerves with a song, healing them");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        healUnit(receivingUnit, receivingUnitStatSheet, this.potency + activeUnitStatSheet.getMagic());
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }

    @Override
    public String getEffectType() {
        return "Heal";
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        return new Label(Integer.toString(this.potency + activeUnitStatSheet.getMagic()));
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }
}
