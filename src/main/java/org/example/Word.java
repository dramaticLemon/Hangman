package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Word {
    final static String patternUserInput = "^[a-zA-Zа-яёА-ЯЁ]+$";

    public static List<Integer> findIndex(char inputChar, String word) {
        List<Integer> indexes = new ArrayList<>();
        String loweCaseChar = Character.toString(inputChar).toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        int index = 0;
        while (index != -1) {
            index = lowerCaseWord.indexOf(loweCaseChar, index);
            if (index != -1) {
                indexes.add(index);
                index++;
            }
        }
        return indexes;
    }

    private static String replaceChar(String str, char ch, List<Integer> indexes) {
        StringBuilder myString = new StringBuilder(str);
        for (Integer index : indexes) {
            myString.setCharAt(index, ch);
        }
        return myString.toString();
    }

    public static void printCurrentVersion(String str, char ch, List<Integer> indexes) {
        Game.puzzleWord = replaceChar(str, ch, indexes);
        System.out.println("Mystery word [ " + Game.puzzleWord + " ]");
    }

    public static char userInputChar(Scanner scanner) {
        String input;
        while (true) {
            System.out.println("Enter a char: ");
            input = scanner.next();
            Pattern pattern = Pattern.compile(patternUserInput);
            boolean matches = pattern.matcher(input).matches();
            if (matches) {
                return  input.charAt(0);
            } else {
                System.out.println("Input Error, please enter a char");
            }
        }
    }

}
