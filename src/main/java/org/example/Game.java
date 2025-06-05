package org.example;

import java.util.*;

public class Game {
    static List<String> bankWords;
    static String guessWord;
    static String puzzleWord;
    static int lifeCount = 7;
    static HangmanDrawer objDrawer = new HangmanDrawer();


    static {
        System.out.println("Are you welcome! The word is hidden, try to guess it! ");
        bankWords = WordLoader.loadWordsResource("/words.txt");
    }


    public static void start (Scanner scanner) {
        guessWord = getWord();
        puzzleWord = guessWord.replaceAll("\\S", "_");

        int winnerCounter = 1;
        int phaseCount = 1;

        Set<Character> guessesLetters = new HashSet<>();
        Set<Character> missedLetters = new LinkedHashSet<>();

        while (true) {
            char inputChar = Word.userInputChar(scanner);
            List<Integer> foundIndexes = Word.findIndex(inputChar, guessWord);

            if (guessesLetters.contains(inputChar) || missedLetters.contains(inputChar)) {
                System.out.println("You already use this latter;");
                continue;
            }

            if (! foundIndexes.isEmpty()) {
                guessesLetters.add(inputChar);
                winnerCounter++;
                Word.printCurrentVersion(puzzleWord, inputChar, foundIndexes);
                if (winnerCounter == guessWord.length()) {
                    System.out.println("Congratulation!");
                    break;
                }
            } else {
                missedLetters.add(inputChar);
                printMissedChar(missedLetters, inputChar);
                lifeCount--;
                Word.printCurrentVersion(puzzleWord, inputChar, foundIndexes);
                printStatus(phaseCount);
                phaseCount++;

                if (lifeCount < 1) {
                    System.out.println("=====GAME OVER=====");
                    System.out.println("Mystical word is:" + guessWord);
                    break;
                }
            }
        }
        lifeCount = 7;
    }

    private static void printMissedChar (Set<Character> missedChar, char c) {
        missedChar.add(c);
        System.out.println("Wrong letters:");
        System.out.println(missedChar);
    }

    private static int getRandomNum () {
        int size = bankWords.size();
        Random obj = new Random();
        return obj.nextInt(0, size);
    }

    public static String getWord () {
        return bankWords.get(getRandomNum());
    }

    public static void printStatus (int phase) {
        System.out.println("Current live: " + lifeCount);
        objDrawer.displayHangman(phase);
    }

}
