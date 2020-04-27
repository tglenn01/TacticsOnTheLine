package main.model.battleSystem;

import main.model.characterSystem.Character;
import main.model.characterSystem.StatSheet;

public class Battle {
    private Character attacker;
    private StatSheet attackerStatSheet;
    private Character defender;
    private StatSheet defenderStatSheet;

    public Battle(Character attacker, Character defender) {
        this.attacker = attacker;
        attackerStatSheet = attacker.getCharacterStatSheet();
        this.defender = defender;
        defenderStatSheet = defender.getCharacterStatSheet();
        Attack();
    }

    private void Attack() {
        int defenderHealth = defenderStatSheet.getHealth();
        int damage = attackerStatSheet.getStrength() - defenderStatSheet.getArmour();
        defenderHealth = defenderHealth - damage;
        if (defenderHealth <= 0) {
            // throw dead exception
        }
    }
}
