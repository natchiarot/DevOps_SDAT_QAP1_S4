package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SuggestionEngineTest {
    private SuggestionEngine suggestionEngine = new SuggestionEngine();

    private boolean testInstanceSame = false;

    @Test
    public void testNumberOfSuggestions() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        String word = "Acrylac";
        String suggestion = suggestionEngine.generateSuggestions(word);
        long generatedSuggestionNumber = suggestion.lines().count();

        Assertions.assertTrue(generatedSuggestionNumber <= 10, "Does not match expected number of suggestions.");
    }

    @Test
    public void testSpecialCharacters() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("acrylic!!").contains("acrylic"), "Does not give the suggestion with special characters");
    }

    @Test
    public void testMoreThanTwoTypos() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertFalse(suggestionEngine.generateSuggestions("akraloc").contains("acrylic"));
    }

    @Test
    public void testIOExceptionThrown() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Path incorrectFilePath = Paths.get("words.txt");

        Assertions.assertThrows(IOException.class, () -> {
            suggestionEngine.loadDictionaryData(incorrectFilePath);
        });
    }
}
