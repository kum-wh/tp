package seedu.mtracker;

import seedu.mtracker.instrument.InstrumentManager;

import java.util.Scanner;

public class MTracker {
    InstrumentManager instrumentManager = InstrumentManager.getInstance();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        //command.setData(instrumentManager, ui); uncomment after initialising ui and command
        System.out.println("Hello " + in.nextLine());
    }
}
