package org.example;

import java.util.*;

public class Game {
    static List<String> bankWords;
    static boolean innerGameFlag = true;
    static String guessWord;
    static Scanner scanner = new Scanner(System.in);
    static String puzzleWord;
    static int lifeCount = 6;
    static int phaseCount = 1;
    static HangmanDrawer objDrawer = new HangmanDrawer();


    static {
        System.out.println("Are you welcome! The word is hidden, try to guess it! ");
        bankWords = WordLoader.loadWordsResource("/words.txt");
    }


    public static void start () {
            guessWord = getWord();
            puzzleWord = guessWord.replaceAll("\\S", "_");

            int winnerCounter = 1;

            Set<Character> guessesLetters = new HashSet<>();
            Set<Character> missedLetters = new LinkedHashSet<>();

            while (innerGameFlag) {
                char inputChar = Word.userInputChar(scanner);
                List<Integer> foundIndexes = Word.findIndex(inputChar, guessWord);

                if (guessesLetters.contains(inputChar)) {
                    System.out.println("You already guessed this letter.");
                    continue;
                }
                if (missedLetters.contains(inputChar)) {
                    System.out.println("You already use this latter;");
                }

                // угадал
                if (! foundIndexes.isEmpty()) {
                    guessesLetters.add(inputChar);
                    winnerCounter++;
                    Word.printCurrentVersion(puzzleWord, inputChar, foundIndexes);
                    if (winnerCounter == guessWord.length()) {
                        innerGameFlag = false;
                    }
                    // не угадал
                } else {
                    missedLetters.add(inputChar);
                    printMissedChar(missedLetters, inputChar);
                    lifeCount--;
                    Word.printCurrentVersion(puzzleWord, inputChar, foundIndexes);
                    printStatus(phaseCount);
                    phaseCount++;

                    if (lifeCount < 1) {
                        innerGameFlag = false;
                        System.out.println("=====GAME OVER=====");
                        System.out.println("Mystical word is:" + guessWord);
                    }
                }
            }
        reset();
    }

    private static void printMissedChar(Set<Character> missedChar, char c) {
        missedChar.add(c);
        System.out.println("Wrong letters:");
        System.out.println(missedChar);
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
