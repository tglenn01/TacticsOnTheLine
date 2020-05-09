package main.exception;

import main.model.characterSystem.CharacterUnit;

public class UnitIsDeadException extends Exception {
    CharacterUnit deadUnit;

    public UnitIsDeadException(CharacterUnit deadUnit) {
        this.deadUnit = deadUnit;
    }

    public CharacterUnit getDeadUnit() {
        return deadUnit;
    }
}
