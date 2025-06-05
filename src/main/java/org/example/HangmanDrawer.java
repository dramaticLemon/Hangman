package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HangmanDrawer {
    public enum HangmanPhase {
        PHASE_1(1),
        PHASE_2(2),
        PHASE_3(3),
        PHASE_4(4),
        PHASE_5(5),
        PHASE_6(6),
        PHASE_7(7);

        private final int phaseNumber;

        HangmanPhase(int phaseNumber) {
            this.phaseNumber = phaseNumber;
        }

        public int getPhaseNumber() {
            return this.phaseNumber;
        }

        public static HangmanPhase fromPhaseNumber(int number) {
            for (HangmanPhase phase : HangmanPhase.values()) {
                if (phase.getPhaseNumber() == number) {
                    return phase;
                }
            }
            throw new IllegalArgumentException("Incorrect phase number " + number);
        }

    }

    private List<String> hangmanDrawings;
    private final String filePrefix = "phase_";
    private final String fileExtension = ".txt";
    private final int maxPhrases = HangmanPhase.values().length;

    public HangmanDrawer() {
        loadHangmanDrawings();
    }

    private void loadHangmanDrawings() {
        hangmanDrawings = new ArrayList<>();

        for (int i = 0; i < maxPhrases; i++) {
            hangmanDrawings.add(null);
        }

        for (int i = 1; i <= maxPhrases; i++) {
            String fileName = "src/main/resources/" + filePrefix + i + fileExtension;
            try {
                String content = new String(Files.readAllBytes(Paths.get(fileName)));

                hangmanDrawings.set(i - 1, content.trim());
            } catch (NoSuchFileException e) {
                System.err.println("Attention: File from phase " + i + " not found: " + fileName);
            } catch (IOException e) {
                System.err.println("Reading error file" + fileName + ": " + e.getMessage());
            }
        }

        boolean allLoaded = true;
        for (int i = 0; i < maxPhrases; i++) {
            if (hangmanDrawings.get(i) == null || hangmanDrawings.get(i).isEmpty()) {
                allLoaded = false;
                break;
            }
        }
        if (!allLoaded) {
            System.err.println("Attention:not all images were loaded");
        }
    }

    public void displayHangman(int phaseNumber) {
        try {
            HangmanPhase phase = HangmanPhase.fromPhaseNumber(phaseNumber);
            int index = phase.getPhaseNumber() - 1;
            if (index >= 0 && index < hangmanDrawings.size() && hangmanDrawings.get(index) != null) {
                System.out.println(hangmanDrawings.get(index));
            } else {
                System.err.println("Error: image from phase " + phaseNumber + "don't load");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main (String[] args) {

    }
}
