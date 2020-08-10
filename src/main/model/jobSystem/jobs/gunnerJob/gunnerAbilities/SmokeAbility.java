package main.model.jobSystem.jobs.gunnerJob.gunnerAbilities;

import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.Blind;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class SmokeAbility extends StatusEffectAbility {
    public SmokeAbility() {
        super("Smoke Screen", 10, 4, 2, 2, 20,
                "Blind an enemy with smoke");
    }

    @Override
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new Blind(receivingUnit, this.potency, this.duration));
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }

    @Override
    public List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        BoardSpace centreSpace = activeUnit.getBoardSpace();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        int x = range;
        int y = 0;
        for (; x >= 0; x--, y++) {
            if ((xPos - x >= 0) && (yPos - y >= 0))
                possibleBoardSpaces.add(boardSpaces[xPos - x][yPos - y]); // top left
            if ((xPos + x < currentBoard.getBoardWidth()) && (yPos - y >= 0))
                possibleBoardSpaces.add(boardSpaces[xPos + x][yPos - y]); // top right
            if ((xPos - x >= 0) && (yPos + y < currentBoard.getBoardHeight()))
                possibleBoardSpaces.add(boardSpaces[xPos - x][yPos + y]); // bottom left
            if ((xPos + x < currentBoard.getBoardWidth()) && (yPos + y < currentBoard.getBoardHeight()))
                possibleBoardSpaces.add(boardSpaces[xPos + x][yPos + y]); // bottom right
        }
        return possibleBoardSpaces;
    }
}
