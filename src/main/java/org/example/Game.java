package org.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    static List<String> bankWords;
    static boolean gameFlag = true;
    static String guessWord;
    static Scanner scanner = new Scanner(System.in);
    static String printingString;
    static int lifeCount = 6;
    static int phaseCount = 1;
    static HangmanDrawer objDrawer = new HangmanDrawer();


    static {
        System.out.println("Game the start, are you welcome!");
        bankWords = WordLoader.loadWordsResource("/words.txt");
    }


    public static void logic() {
        while (gameFlag) {

            System.out.println("Start new game(s) or exit(ex)");

            String input = scanner.next();
            if (input.equalsIgnoreCase("ex")) {
                System.out.println("Exit ...");
                gameFlag = false;
            }

            guessWord = getWord();
            printingString = guessWord.replaceAll("\\S", "_");
            boolean innerFlag = true;
            int counter = 1;

            while (innerFlag) {

                char inputChar = Word.userInputChar(scanner);
                List<Integer> list = Word.findIndex(inputChar, guessWord);

                if (! list.isEmpty()) {
                    System.out.println(printingString);
                    printStatus(phaseCount);
                    Word.printCurrentVersion(printingString, inputChar, list);
                    counter++;
                    if (counter == guessWord.length()) {
                        innerFlag = false;
                    }
                } else {
                    lifeCount--;
                    phaseCount++;
                    Word.printCurrentVersion(printingString, inputChar, list);
                    printStatus(phaseCount);
                    if (lifeCount < 1) {
                        innerFlag = false;
                        System.out.println("=====GAME OVER=====");
                    }

                }
            }
        reset();
        }
        scanner.close();
    }

    private static int getRandomNum() {
        int size = bankWords.size();
        Random obj = new Random();
        return obj.nextInt(0, size);
    }

    public static String getWord() {
        return bankWords.get(getRandomNum());
    }

    public static void printStatus (int phase) {
        System.out.println("Current life count: " + lifeCount);
        objDrawer.displayHangman(phase);
    }
    private static void reset() {
        lifeCount = 6;
        phaseCount = 1;
    }


}
