package main.ui;

import java.util.Scanner;

public class UserInput {

    public UserInput() {
    }

    public String getInput() {
        boolean waitingForInput = true;
        String command = null;
        Scanner input = new Scanner(System.in);

        while (waitingForInput) {
            command = input.next();
            if (command != null) {
                waitingForInput = false;
            }
        }
        return command;
    }
}
