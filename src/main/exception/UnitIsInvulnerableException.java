package main.exception;

public class UnitIsInvulnerableException extends Exception {

    public void printInvulnerableMessage() {
        System.out.println("Unit was invulnerable! They took no damage");
    }
}
