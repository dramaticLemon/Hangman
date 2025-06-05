package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    static List<String> bankWords;
    static boolean gameFlag = true;
    static boolean innerGameFlag = true;
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
            System.out.println(guessWord);
            printingString = guessWord.replaceAll("\\S", "_");
            int counter = 1;

            List<Character> guessesLetters = new ArrayList<>();

            while (innerGameFlag) {

                char inputChar = Word.userInputChar(scanner);
                List<Integer> indexesList = Word.findIndex(inputChar, guessWord);

                if (! indexesList.isEmpty()) {

                    if (guessesLetters.contains(inputChar)) {
                        System.out.println("Ты уже отгадал эту букву");
                        continue;
                    }

                    System.out.println(printingString);
                    printStatus(phaseCount);
                    Word.printCurrentVersion(printingString, inputChar, indexesList);
                    guessesLetters.add(inputChar);
                    counter++;
                    if (counter == guessWord.length()) {
                        innerGameFlag = false;
                    }
                } else {
                    System.out.println("Current life " + lifeCount);
                    if (guessesLetters.contains(inputChar)) {
                        System.out.println("Ты уже отгадал эту букву");
                        continue;
                    }
                    lifeCount--;
                    phaseCount++;
                    Word.printCurrentVersion(printingString, inputChar, indexesList);
                    printStatus(phaseCount);

                    if (lifeCount < 1) {
                        innerGameFlag = false;
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
        innerGameFlag = true;
    }


}
