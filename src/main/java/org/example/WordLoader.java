package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordLoader {
    
    public static List<String> loadWordsResource(String resourcePath) {
        List<String> words = new ArrayList<>();
        try (InputStream is = WordLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Error: Resource not found at path: " + resourcePath);
                return words;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        words.add(line.trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Load Error Resource: " + e.getMessage());
        }
        return words;
    }
}
