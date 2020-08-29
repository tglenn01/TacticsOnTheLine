package main.model.combatSystem.targetTypes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.TargetType;
import main.model.graphics.menus.BattleMenu;
import main.ui.TacticBaseBattle;

import java.util.LinkedList;
import java.util.List;

public class SelfTarget extends TargetType {

    @Override
    public List<BoardSpace> getTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        LinkedList<BoardSpace> boardSpaces = new LinkedList<>();
        boardSpaces.add(centreSpace);
        return boardSpaces;
    }

    @Override
    public void setHandlers(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
        SelfTargetHandler selfTargetHandler = new SelfTargetHandler(activeUnit, chosenAbility, possibleBoardSpaces);
        activeUnit.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, selfTargetHandler);
    }


    protected static class SelfTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;

        public SelfTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                activeUnit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                activeUnit.takeAction(chosenAbility, possibleBoardSpaces);
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                activeUnit.getCharacterSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            }
        }
    }
}
