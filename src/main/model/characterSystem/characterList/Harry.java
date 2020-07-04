package main.model.characterSystem.characterList;

import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatBonus;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.personalAbilities.TremorAbility;
import main.model.jobSystem.jobs.Warrior;

import static main.model.graphics.sceneElements.images.CharacterPortrait.ESTELLE_PORTRAIT;
import static main.model.graphics.sceneElements.images.CharacterSprite.ESTELLE_SPRITE;

public class Harry extends PlayableCharacterUnit {
    private final int personalHealthBoost = 8;
    private final int personalManaBoost = 0;
    private final int personalStrengthBoost = 6;
    private final int personalMagicBoost = 0;
    private final int personalArmourBoost = 4;
    private final int personalResistanceBoost = 0;
    private final int personalSpeedBoost = 2;
    private final int personalDexterityBoost = 2;

    public Harry() {
        this.characterName = "Harry North";
        this.setCharacterPortrait(ESTELLE_PORTRAIT);
        this.setCharacterSprite(ESTELLE_SPRITE);
    }

    @Override
    protected void setBaseJob() {
        this.setJob(new Warrior());
    }

    @Override
    protected void setPersonalAbility() {
        this.personalAbility = new TremorAbility("Tremor", 4, 0, 2,
                Ability.AbilityType.DAMAGE, 6, 1.00,
                "Damage neighbouring enemies while stopping their movement");
        addPersonalAbilityToAbilityList();
    }

    @Override
    protected void setPersonalStatBonuses() {
        this.personalStatBonus = new StatBonus(personalHealthBoost, personalManaBoost, personalStrengthBoost, personalMagicBoost,
                personalArmourBoost, personalResistanceBoost, personalSpeedBoost, personalDexterityBoost);
    }
}
