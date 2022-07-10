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

    public int calculateHighestFrequency(String text) {

        List<WordsFrequencies> wordsFrequenciesList = getWordAndFrequencyList(text);

        int highestFrequency = 0;
        for (WordFrequency wordFrequency : wordsFrequenciesList) {
            if (wordFrequency.getFrequency() > highestFrequency) {
                highestFrequency = wordFrequency.getFrequency();
            }
        }

        return highestFrequency;
    };

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
        List<WordsFrequencies> wordsFrequenciesList = getWordAndFrequencyList(text);
        for (WordFrequency wordFreq : wordsFrequenciesList) {
            if (wordFreq.getWord().equalsIgnoreCase(word)) {
                wordFrequency = wordFreq.getFrequency();
                break;
            }
        }

        return wordFrequency;
    };

    public List<WordFrequency> calculateMostFrequentNWords (String text, int n) {

        Map<String, Integer> mapOfFrequentWords = new TreeMap<>();

        List<WordsFrequencies> wordsFrequenciesList = getWordAndFrequencyList(text);

        for (WordFrequency wordFrequency : wordsFrequenciesList) {
            if (!mapOfFrequentWords.containsKey(wordFrequency.getWord())) {
                mapOfFrequentWords.put(wordFrequency.getWord(), wordFrequency.getFrequency());
            }
        }

        Map<String, Integer> sorted = mapOfFrequentWords
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        List<WordFrequency> wordFrequencies = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            WordsFrequencies wordsFrequencies = new WordsFrequencies();
            wordsFrequencies.setWord(entry.getKey());
            wordsFrequencies.setFrequency(entry.getValue());

            wordFrequencies.add(wordsFrequencies);
        }

        return wordFrequencies;
    }

    private static List<WordsFrequencies> getWordAndFrequencyList(String text) {

        List<WordsFrequencies> wordFrequencyList = new ArrayList<>();

        if (text == null || text.isEmpty()) return wordFrequencyList;

        // Will replace every character that's not a letter with a space
        String resultString = text.replaceAll("\\P{L}", " ");
        resultString = resultString.trim().replaceAll(" +", " ");

        // If the text string only had special characters
        if (resultString.isEmpty()) return wordFrequencyList;

        String[] words = resultString.split(" ");

        for (String word : words) {

            boolean found = false;

            for (WordsFrequencies wordFrequency : wordFrequencyList) {
                if (wordFrequency.getWord().equalsIgnoreCase(word)) {
                    int frequencyIncremented = wordFrequency.getFrequency() + 1;
                    wordFrequency.setFrequency(frequencyIncremented);
                    found = true;
                    break;
                }
            }

            if (!found) {
                WordsFrequencies wordFrequency = new WordsFrequencies();
                wordFrequency.setFrequency(1);
                wordFrequency.setWord(word.toLowerCase());
                wordFrequencyList.add(wordFrequency);
            }
        }

        return wordFrequencyList;
    }
}
