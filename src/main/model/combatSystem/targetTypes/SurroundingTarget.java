package main.model.combatSystem.targetTypes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.AbilityPreview;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.battleSystem.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SurroundingTarget extends TargetType {

    @Override
    public void setTargetTypeImage() {
        this.targetTypeImage = new ImageView(new Image("resources/targetTypeImages/SurroundingTargetType.png"));
    }


    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        if (xPos - 1 >= 0) possibleBoardSpaces.add(boardSpaces[xPos - 1][yPos]);
        if (yPos - 1 >= 0) possibleBoardSpaces.add(boardSpaces[xPos][yPos - 1]);
        if (xPos + 1 < currentBoard.getBoardWidth()) possibleBoardSpaces.add(boardSpaces[xPos + 1][yPos]);
        if (yPos + 1 < currentBoard.getBoardHeight()) possibleBoardSpaces.add(boardSpaces[xPos][yPos + 1]);
        return possibleBoardSpaces;
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        List<CharacterUnit> possibleTargets = new ArrayList<>();
        possibleBoardSpaces.forEach(space -> {
            if (space.isOccupied()) possibleTargets.add(space.getOccupyingUnit());
        });

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
            boardSpace.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);
        }


        SurroundingTargetHandler surroundingTargetHandler = new SurroundingTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces, possibleTargets);

        setHandlersToNodes(surroundingTargetHandler, possibleBoardSpaces, possibleTargets);
    }


    private class SurroundingTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        List<AbilityPreview> displayingAbilityPreviews = new LinkedList<>();
        boolean highlighted = false;
        private int cursor = 0;

        public SurroundingTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (repeatingCall(event)) return;

            if (event.getButton() == MouseButton.PRIMARY) {
                if (cursor == 0) {
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    possibleBoardSpaces.forEach(space -> space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR));
                    for (BoardSpace highlightedSpace : possibleBoardSpaces) {
                        if (highlightedSpace.isOccupied()) {
                            AbilityPreview abilityPreview = new AbilityPreview(activeUnit, highlightedSpace.getOccupyingUnit(), chosenAbility);
                            displayingAbilityPreviews.add(abilityPreview);
                        }
                    }

                    cursor++;
                    highlighted = true;
                } else {
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    displayingAbilityPreviews.forEach(Stage::close);
                    activeUnit.takeAction(chosenAbility, possibleBoardSpaces);
                }

            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (cursor == 0) {
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                    BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                } else {
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
                    possibleBoardSpaces.forEach(space -> space.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
                    possibleBoardSpaces.forEach(space -> space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR));
                    maintainHighlightOnMouseSpace(event);
                    displayingAbilityPreviews.forEach(Stage::close);
                    cursor--;
                    highlighted = false;
                }

            }
        }

        private void maintainHighlightOnMouseSpace(MouseEvent event) {
            BoardSpace boardSpace;
            if (event.getSource().getClass() == BoardSpace.class) {
                boardSpace = (BoardSpace) event.getSource();

            } else {
                CharacterSprite sprite = (CharacterSprite) event.getSource();
                boardSpace = sprite.getUnit().getBoardSpace();
            }
            if (possibleBoardSpaces.contains(boardSpace))
                boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);
        }

        // as boardSpace and characterSprite overlap, the handle is called twice, this prevents that
        private boolean repeatingCall(Event event) {
            if (event.getSource().getClass() == BoardSpace.class) {
                BoardSpace boardSpace = (BoardSpace) event.getSource();
                return boardSpace.isOccupied();
            }
            return false;
        }
    }
}
