package main.model.combatSystem;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.boardSystem.BoardSpace;
import main.model.boardSystem.landTypes.LandType;
import main.model.characterSystem.CharacterUnit;
import main.model.graphics.menus.AbilityPreview;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.model.battleSystem.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class TargetType {
    protected ImageView targetTypeImage;
    protected EventHandler<MouseEvent> hoverHandler = event -> {
        BoardSpace boardSpace =  (BoardSpace) event.getSource();
        boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);
    };
    protected EventHandler<MouseEvent> exitHandler = event -> {
        BoardSpace boardSpace = (BoardSpace) event.getSource();
        boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
    };



    public TargetType() {
        setTargetTypeImage();
    }


    public abstract void setTargetTypeImage();

    public ImageView getTargetTypeImage() {
        return this.targetTypeImage;
    }

    public abstract List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility);

    // Overloaded in MovementTarget private class in the MovementAbility class, which will display
    // movement spaces instead which requires activeUnit
    public void displayTargets(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) {
        TacticBaseBattle.getInstance().getCurrentBoard().displayAbilitySpaces(possibleBoardSpaces);
    }

    public abstract void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces);

    protected void setHandlersToNodes(EventHandler<MouseEvent> handle, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
        if (possibleBoardSpaces != null) {
            for (BoardSpace boardSpace : possibleBoardSpaces) {
                boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }

        if (possibleTargets != null) {
            for (CharacterUnit unit : possibleTargets) {
                unit.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }
    }

    protected void removeHandlersFromNodes(EventHandler<MouseEvent> handle, List<BoardSpace> possibleBoardSpaces, List<CharacterUnit> possibleTargets) {
        if (possibleBoardSpaces != null) {
            for (BoardSpace boardSpace : possibleBoardSpaces) {
                boardSpace.removeEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }

        if (possibleTargets != null) {
            for (CharacterUnit unit : possibleTargets) {
                unit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, handle);
            }
        }
    }


    // Hovering over board space: changes colour of said space
    // Click 1: display what board spaces are effected when targeting that space and display Ability Preview
    // Click 2: ability takes effect
    protected class ApplyTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private CharacterUnit highlightedUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private List<BoardSpace> highlightedSpaces = new ArrayList<>();
        private List<AbilityPreview> displayingAbilityPreview = new LinkedList<>();
        private int cursor = 0;


        public ApplyTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces,
                                  List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (repeatingCall(event)) return;

            if (event.getButton() == MouseButton.SECONDARY) {
                if (cursor == 0) {
                    returnToMenu();
                } else {
                    revertToTargeting(event);
                }
            } else if (event.getButton() == MouseButton.PRIMARY){
                if (event.getSource().getClass().getSuperclass() != CharacterSprite.class) return;

                CharacterSprite targetSprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = targetSprite.getUnit();

                if (highlightedUnit != null && targetUnit != highlightedUnit) {
                    revertToTargeting(event);
                } else if (cursor == 0) {
                    abilityPreview(targetUnit);
                } else if (cursor == 1 && targetUnit == highlightedUnit) {
                    useAbility();
                }
            }
        }

        // remove handlers and use abilities on targets
        private void useAbility() {
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
            displayingAbilityPreview.forEach(Stage::close);

            activeUnit.takeAction(chosenAbility, highlightedSpaces);
        }

        // display what units will be targeted at that space and display ability preview
        private void abilityPreview(CharacterUnit targetUnit) {
            TargetType areaOfEffectTargetType = chosenAbility.getTargetType();
            List<BoardSpace> effectedBoardSpaces = areaOfEffectTargetType.getTargetPattern(targetUnit.getBoardSpace(), chosenAbility.getAreaOfEffect() - 1, chosenAbility);
            effectedBoardSpaces.forEach(boardSpace -> {
                boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);
                if (!possibleBoardSpaces.contains(boardSpace)) boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
            });
            highlightedSpaces.addAll(effectedBoardSpaces);
            highlightedUnit = targetUnit;
            cursor++;
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));

            for (BoardSpace highlightedSpace : highlightedSpaces) {
                if (highlightedSpace.isOccupied()) {
                    AbilityPreview abilityPreview = new AbilityPreview(activeUnit, highlightedSpace.getOccupyingUnit(), chosenAbility);
                    displayingAbilityPreview.add(abilityPreview);
                }
            }
        }

        // stop showing the ability preview and return to choosing a board space
        private void revertToTargeting(Event event) {
            highlightedSpaces.forEach(space -> {
                if (!possibleBoardSpaces.contains(space)) space.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                if (possibleBoardSpaces.contains(space))
                    space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.ABILITY_HIGHLIGHT_COLOUR);
                else if (TacticBaseBattle.getInstance().getCurrentBoard().isSpaceMovementHighlighted(space))
                    space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NON_ACTIVE_UNIT_MOVEMENT_HIGHLIGHT_COLOUR);
                else space.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.NORMAL);
            });
            highlightedSpaces.clear();
            for (BoardSpace boardSpace : possibleBoardSpaces) {
                boardSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler);
                boardSpace.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);
            }
            cursor = 0;
            highlightedUnit = null;

            BoardSpace boardSpace;
            if (event.getSource().getClass() == BoardSpace.class) {
                boardSpace = (BoardSpace) event.getSource();

            } else {
                CharacterSprite sprite = (CharacterSprite) event.getSource();
                boardSpace = sprite.getUnit().getBoardSpace();
            }
            if (possibleBoardSpaces.contains(boardSpace))
                boardSpace.changeSpaceColour(LandType.BOARD_SPACE_HIGHLIGHT_COLOUR.HOVER_HIGHLIGHT_COLOR);

            displayingAbilityPreview.forEach(Stage::close);
        }

        // stop showing ability spaces and return to the menu
        private void returnToMenu() {
            removeHandlersFromNodes(this, possibleBoardSpaces, possibleTargets);
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_ENTERED, hoverHandler));
            possibleBoardSpaces.forEach(space -> space.removeEventHandler(MouseEvent.MOUSE_EXITED, exitHandler));
            BattleMenu.getInstance().displayCharacterMenu(activeUnit);
            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
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
