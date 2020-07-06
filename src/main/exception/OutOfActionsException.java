package main.exception;

public class OutOfActionsException extends Exception {

    public void printOutOfActionsError() {
        System.out.println("You have no more ability actions");
    }
}
