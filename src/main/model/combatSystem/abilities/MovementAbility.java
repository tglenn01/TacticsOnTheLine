package main.model.combatSystem.abilities;

import javafx.event.EventHandler;
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
        TacticBaseBattle.getInstance().getCurrentBoard().displayValidSpaces(activeUnit, activeUnit.getCharacterStatSheet().getMovement());
        List<BoardSpace> spaces = activeUnit.getMovementRange();
        for (BoardSpace space : spaces) {
            space.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TacticBaseBattle.getInstance().getCurrentBoard().stopShowingDisplayedSpaces(activeUnit);
                    for (BoardSpace space : spaces) {
                        space.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                    if (space.getOccupyingUnit() != activeUnit) {
                        activeUnit.getBoardSpace().removeOccupyingUnit();
                        space.setOccupyingUnit(activeUnit);
                        TacticBaseBattle.getInstance().getCurrentBoard().stopShowingDisplayedSpaces(activeUnit);
                        activeUnit.movementComplete(TacticBaseBattle.getInstance().getBattle());
                    } else {
                        TacticBaseBattle.getInstance().getCurrentBoard().stopShowingDisplayedSpaces(activeUnit);
                        AbilityMenu.display(activeUnit, TacticBaseBattle.getInstance().getBattle(), activeUnit.getCharacterJob().getJobAbilityList());
                    }
                    event.consume();
                }
            });
        }
    }
}
