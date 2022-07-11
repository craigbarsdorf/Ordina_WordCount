package nl.ordina.wordcount.service;

import nl.ordina.wordcount.dto.WordFrequency;
import nl.ordina.wordcount.dto.WordsFrequencies;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {

    private WordsFrequencies wordsFrequencies;

    public WordFrequencyAnalyzerImpl(WordsFrequencies wordsFrequencies) {
        this.wordsFrequencies = wordsFrequencies;
    }

    /**
     * Gets the amount of times the word with the highest frequency appeared in the given text string.
     * @param text This is the string of text characters, delimited by special characters
     * @return highestFrequency This is the number of times the word with the highest frequency appeared.
     */
    @Override
    public int calculateHighestFrequency(String text) {

        List<WordFrequency> wordsFrequenciesList = getWordAndFrequencyList(text);

        int highestFrequency = 0;
        for (WordFrequency wordFrequency : wordsFrequenciesList) {
            if (wordFrequency.getFrequency() > highestFrequency) {
                highestFrequency = wordFrequency.getFrequency();
            }
        }

        return highestFrequency;
    };

    /**
     * Gets the amount of times a specified word appears in the given text string.
     * @param text This is the string of text characters, delimited by special characters
     * @param word This is the word that must be searched for and counted in the given text string.
     * @return highestFrequency This is the number of times the given word appears in the given text string.
     */
    @Override
    public int calculateFrequencyForWord (String text, String word) {

        int wordFrequency = 0;

        // Check that the word is valid
        if (!Pattern.matches("[a-zA-Z]+",word)) {
            return wordFrequency;
        }

        // Check if the word is in the String
        if (!text.toLowerCase().contains(word.toLowerCase())) {
            return wordFrequency;
        }

        // Get the frequency of the word in the string
        List<WordFrequency> wordsFrequenciesList = getWordAndFrequencyList(text);
        for (WordFrequency wordFreq : wordsFrequenciesList) {
            if (wordFreq.getWord().equalsIgnoreCase(word)) {
                wordFrequency = wordFreq.getFrequency();
                break;
            }
        }

        return wordFrequency;
    };

    /**
     * Gets the amount of times the specified number of words appear in the given text string.
     * @param text This is the string of text characters, delimited by special characters
     * @param n This is the number of words that must be search for in the given string.
     *            These would be the words with the highest frequencies.
     * @return List<WordFrequency> This is the list of words, as per the requested amount of words,
     *         that appear in the given text string.
     */
    @Override
    public List<WordFrequency> calculateMostFrequentNWords (String text, int n) {

        Map<String, Integer> mapOfFrequentWords = new TreeMap<>();

        // Get the initial list of words and the amount of times they appear in the given text string
        List<WordFrequency> wordsFrequenciesList = getWordAndFrequencyList(text);

        // Put the list of words and their frequencies into a map
        for (WordFrequency wordFrequency : wordsFrequenciesList) {
            if (!mapOfFrequentWords.containsKey(wordFrequency.getWord())) {
                mapOfFrequentWords.put(wordFrequency.getWord(), wordFrequency.getFrequency());
            }
        }

        // Sort the map of words and their frequencies, and only keep the amount of words with the highest frequencies
        // requested
        Map<String, Integer> sorted = mapOfFrequentWords
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        List<WordFrequency> wordFrequencies = new ArrayList<>();

        // Put the map of words and their frequencies into a list of WordFrequency objects
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            WordsFrequencies wordsFrequencies = new WordsFrequencies();
            wordsFrequencies.setWord(entry.getKey());
            wordsFrequencies.setFrequency(entry.getValue());

            wordFrequencies.add(wordsFrequencies);
        }

        return wordFrequencies;
    }

    // Gets all the words in the given text string, and counts the amount of times the word is in the string
    private static List<WordFrequency> getWordAndFrequencyList(String text) {

        List<WordFrequency> wordsfrequencies = new ArrayList<>();

        if (text == null || text.isEmpty()) return wordsfrequencies;

        // Will replace every character that's not a letter with a space
        String resultString = text.replaceAll("\\P{L}", " ");
        resultString = resultString.trim().replaceAll(" +", " ");

        // If the text string only had special characters
        if (resultString.isEmpty()) return wordsfrequencies;

        String[] words = resultString.split(" ");

        List<WordsFrequencies> wordFrequencyList = new ArrayList<>();

        for (String word : words) {

            boolean found = false;

            // If the word is in the list already, then increment its frequency by 1.
            for (WordsFrequencies wordFrequency : wordFrequencyList) {
                if (wordFrequency.getWord().equalsIgnoreCase(word)) {
                    int frequencyIncremented = wordFrequency.getFrequency() + 1;
                    wordFrequency.setFrequency(frequencyIncremented);
                    found = true;
                    break;
                }
            }

            // If the word was not found in the list, then add it to the list and initialize its frequency to 1.
            if (!found) {
                WordsFrequencies wordFrequency = new WordsFrequencies();
                wordFrequency.setFrequency(1);
                wordFrequency.setWord(word.toLowerCase());
                wordFrequencyList.add(wordFrequency);
            }
        }

        // Make the words frequencies list immutable
        wordsfrequencies.addAll(wordFrequencyList);

        return wordsfrequencies;
    }
}
