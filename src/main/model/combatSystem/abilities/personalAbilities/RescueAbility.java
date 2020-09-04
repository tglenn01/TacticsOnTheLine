package main.model.combatSystem.abilities.personalAbilities;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.battleSystem.TacticBaseBattle;

public class RescueAbility extends Ability {
    private CharacterUnit unitWithThisAbility;

    public RescueAbility(CharacterUnit unitWithThisAbility) {
        super("Rescue", 10, 1, 1,
                "Teleport a far away ally to your side");
        this.unitWithThisAbility = unitWithThisAbility;
    }

    @Override
    public String getEffectType() {
        return "Support";
    }

    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        BoardSpace closetBoardSpace = TacticBaseBattle.getInstance().getCurrentBoard().getClosetBoardSpace(activeUnit.getBoardSpace());
        receivingUnit.setBoardSpace(closetBoardSpace);
        return true;
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
    public Node getExpectedResultsLabel(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        ImageView imageView = new ImageView(new Image("resources/weapons/CrystalStaff.png"));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(42);
        return imageView;
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }


    @Override
    public boolean targetsAlly() {
        return true;
    }


    @Override
    public int getRange() {
        return unitWithThisAbility.getCharacterStatSheet().getMagic();
    }
}
