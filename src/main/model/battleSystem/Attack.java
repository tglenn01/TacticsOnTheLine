package main.model.battleSystem;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;

public class Attack {
    private CharacterUnit attacker;
    private StatSheet attackerStatSheet;
    private CharacterUnit defender;
    private StatSheet defenderStatSheet;

    public Attack(CharacterUnit attacker, CharacterUnit defender) {
        this.attacker = attacker;
        attackerStatSheet = attacker.getCharacterStatSheet();
        this.defender = defender;
        defenderStatSheet = defender.getCharacterStatSheet();
        calculateDamageDone();
    }

    private void calculateDamageDone() {
        int defenderHealth = defenderStatSheet.getHealth();
        int damage = attackerStatSheet.getStrength() - defenderStatSheet.getArmour();
        defenderHealth = defenderHealth - damage;
        if (defenderHealth <= 0) {
            // throw dead exception
        }
    }
}
