package main.model.combatSystem.abilities.personalAbilities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.Invulnerable;

public class BarrierAbility extends Ability {

    public BarrierAbility() {
        super("Barrier", 14, 1, 1,
                "Protect an ally from damage one time");
    }

    @Override
    public String getEffectType() {
        return "Support";
    }

    @Override
    public boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Invulnerable(receivingUnit, 1);
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
        return new ImageView(Invulnerable.getStaticIcon());
    }

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }
}
