package main.model.combatSystem.abilities;

import javafx.scene.Node;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.targetTypes.MovementTarget;

import java.util.List;

public class MovementAbility extends Ability {

    public MovementAbility() {
        super("Move", 0, 0, 0,
                 "Move your character");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        activeUnit.setBoardSpace(targetedBoardSpaces.get(0));
    }

    @Override
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return null;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }


    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        // this will not be called as it is called from the super takeAction which will not resolve in this class
        return false;
    }

    @Override
    public String getEffectType() {
        return "Movement";
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
        return false;
    }

    @Override
    public void setTargetType() {
        this.targetType = new MovementTarget();
    }


}
