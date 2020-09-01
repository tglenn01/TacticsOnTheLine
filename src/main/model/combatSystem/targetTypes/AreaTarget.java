package main.model.combatSystem.targetTypes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class AreaTarget extends TargetType {

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/AreaTargetType.png"));
    }

    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        for (int x = 1; x <= range; x++) {
            if (xPos - x >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos - x][yPos]); // left
            if (xPos + x < currentBoard.getBoardWidth())
                possibleBoardSpaces.add(boardSpaces[xPos + x][yPos]); // right
            if (yPos + x < currentBoard.getBoardHeight())
                possibleBoardSpaces.add(boardSpaces[xPos][yPos + x]); // up
            if (yPos - x >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos][yPos - x]); // down

            for (int y = 1; y <= range; y++) {
                if (x + y <= range) {
                    if ((xPos - x >= 0) && (yPos - y >= 0))
                        possibleBoardSpaces.add(boardSpaces[xPos - x][yPos - y]); // top left
                    if ((xPos + x < currentBoard.getBoardWidth()) && (yPos - y >= 0))
                        possibleBoardSpaces.add(boardSpaces[xPos + x][yPos - y]); // top right
                    if ((xPos - x >= 0) && (yPos + y < currentBoard.getBoardHeight()))
                        possibleBoardSpaces.add(boardSpaces[xPos - x][yPos + y]); // bottom left
                    if ((xPos + x < currentBoard.getBoardWidth()) && (yPos + y < currentBoard.getBoardHeight()))
                        possibleBoardSpaces.add(boardSpaces[xPos + x][yPos + y]); // bottom right
                }
            }
        }

        possibleBoardSpaces.add(centreSpace);

        return possibleBoardSpaces;
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
            boardSpace.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);
        }

        ApplyTargetHandler applyTargetHandler = new ApplyTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets);
        setHandlersToNodes(applyTargetHandler, possibleBoardSpaces, possibleTargets);
    }
}
