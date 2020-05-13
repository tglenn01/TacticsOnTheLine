package main.exception;

import main.model.characterSystem.CharacterUnit;

public class AttackMissedException extends Exception {
    private CharacterUnit unitThatDodged;

    public AttackMissedException(CharacterUnit unitThatDodged) {
        this.unitThatDodged = unitThatDodged;
    }

    public void printMissedAttackMessage() {
        System.out.println("Attack missed " + unitThatDodged.getCharacterName() + "!");
    }
}
