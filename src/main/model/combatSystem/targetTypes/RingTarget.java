package main.model.combatSystem.targetTypes;

import javafx.scene.input.MouseEvent;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class RingTarget extends TargetType {
    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
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

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);

        ReturnToMenuHandler returnToMenuHandler = new ReturnToMenuHandler(activeUnit, possibleBoardSpaces, possibleTargets);
        ApplyTargetHandler applyTargetHandler = new ApplyTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets, returnToMenuHandler);
        returnToMenuHandler.setApplyTargetHandler(applyTargetHandler);

        for (CharacterUnit possibleTarget : possibleTargets) {
            possibleTarget.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, applyTargetHandler);
        }

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, returnToMenuHandler);
        }
    }
}
