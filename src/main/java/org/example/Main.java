package org.example;


import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {
        while (true) {
            if (askStartGame()) {
                Game.start();
            }
            else {
                scanner.close();
                System.out.println("Exited...");
                System.exit(0);
            }
        }

    }

    private static boolean askStartGame() {
        System.out.println("Do you want start game ? (y/n)");
        while (true) {
            String input = Main.scanner.next().toLowerCase();

            switch (input) {
                case "n" -> {
                    return false;
                }
                case "y" -> {
                    return true;
                }
                default -> System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}