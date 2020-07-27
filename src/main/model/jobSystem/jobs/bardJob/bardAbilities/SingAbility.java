package main.model.jobSystem.jobs.bardJob.bardAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.List;

public class SingAbility extends StatusEffectAbility {
    public SingAbility() {
        super("Sing", 10, 4, 2, 1, 4,
                "Calm your allies nerves with a song, healing them");
    }

    @Override
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        healUnit(receivingUnit, receivingUnitStatSheet, this.potency + activeUnitStatSheet.getMagic());
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            resolveEffect(activeUnit, boardSpace.getOccupyingUnit());
        }
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
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range, this);
    }
}