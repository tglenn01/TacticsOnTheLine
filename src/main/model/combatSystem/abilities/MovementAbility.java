package main.model.combatSystem.abilities;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.graphics.menus.AbilityMenu;
import main.ui.TacticBaseBattle;

import java.util.List;

public class MovementAbility extends Ability {


    public MovementAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                           Ability.AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        TacticBaseBattle.getInstance().getCurrentBoard().displayValidMovementSpaces(activeUnit, activeUnit.getCharacterStatSheet().getMovement());
        List<BoardSpace> spaces = activeUnit.getMovementRange();
        for (BoardSpace space : spaces) {
            space.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        List<CharacterUnit> list = TacticBaseBattle.getInstance().getCurrentBoard().getMovementHighlightedSpaces().get(space);

                        if (space.getOccupyingUnit() != activeUnit && list.contains(activeUnit)) {
                            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                            activeUnit.getBoardSpace().removeOccupyingUnit();
                            space.setOccupyingUnit(activeUnit);
                            TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                            activeUnit.movementComplete(TacticBaseBattle.getInstance().getBattle());
                        }
                    }

                    if (event.getButton() == MouseButton.SECONDARY) {
                        TacticBaseBattle.getInstance().getCurrentBoard().stopShowingMovementSpaces(activeUnit);
                        if (!AbilityMenu.isDisplaying()) {
                            AbilityMenu.display(activeUnit, activeUnit.getCharacterJob().getJobAbilityList());
                        }
                    }

                    space.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    event.consume();
                }
            });
        }
    }
}
