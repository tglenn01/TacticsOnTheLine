package main.model.combatSystem.targetTypes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;

import java.util.LinkedList;
import java.util.List;

public class SelfTarget extends TargetType {

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/SelfTargetType.png"));
    }



    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        LinkedList<BoardSpace> boardSpaces = new LinkedList<>();
        boardSpaces.add(centreSpace);
        return boardSpaces;
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = new LinkedList<>();
        possibleTargets.add(activeUnit);

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
            boardSpace.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);
        }

        ApplyTargetHandler applyTargetHandler = new ApplyTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets);
        setHandlersToNodes(applyTargetHandler, possibleBoardSpaces, possibleTargets);
    }
}
