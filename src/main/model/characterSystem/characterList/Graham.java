package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.personalAbilities.TremorAbility;
import main.model.jobSystem.jobs.Archer;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;
import static main.model.graphics.sceneElements.images.CharacterSprite.ESTELLE_SPRITE;

public class Graham extends PlayableCharacterUnit {
    private final int personalHealthBoost = 0;
    private final int personalManaBoost = 2;
    private final int personalStrengthBoost = 2;
    private final int personalMagicBoost = 4;
    private final int personalArmourBoost = 2;
    private final int personalResistanceBoost = 4;
    private final int personalSpeedBoost = 4;
    private final int personalDexterityBoost = 6;

    public Graham() {
        this.characterName = "Graham Lost";
        this.setCharacterPortrait(ESTELLE_PORTRAIT);
        this.setCharacterSprite(ESTELLE_SPRITE);
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Archer());
    }

    protected void setPersonalAbility() {
        this.personalAbility = new TremorAbility("Tremor", 4, 0, 2,
                Ability.AbilityType.DAMAGE, 6, 1.00,
                "Damage neighbouring enemies while stopping their movement");
        addPersonalAbilityToAbilityList();
    }



    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }

    protected void setGrowthRate() {
        // stub
    }
}
