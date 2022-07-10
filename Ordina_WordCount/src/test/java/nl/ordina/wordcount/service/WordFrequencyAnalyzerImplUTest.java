package nl.ordina.wordcount.service;

import nl.ordina.wordcount.dto.WordFrequency;
import nl.ordina.wordcount.dto.WordsFrequencies;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class WordFrequencyAnalyzerImplUTest {

    @InjectMocks
    private WordsFrequencies wordsFrequencies;

    private WordFrequencyAnalyzerImpl testObj;

    private static final String TEXT_STRING = "The apple lay/-+!@#$%^&());:[]{}|ON THE table that is on a carpet," +
            " in a bowel with more of the apples";
    private static final String SPECIAL_CHARACTER_STRING = "/-+!@#$%^&())\\\";:[]{}\\\\ |";
    private static final String WORD_STRING = "the";
    private static final String EMPTY_WORD_STRING = "";
    private static final String EMPTY_TEXT_STRING = "";

    @Before
    public void setUp() {
        testObj = new WordFrequencyAnalyzerImpl(wordsFrequencies);
    }

    @Test
    public void existingTextString_calculateHighestFrequency_returnsHighestFrequency() {
        int result = testObj
                .calculateHighestFrequency(TEXT_STRING);
        assertEquals(3, result);
    };

    @Test
    public void nullString_calculateHighestFrequency_returnsZeroFrequency() {
        int result = testObj
                .calculateHighestFrequency(null);
        assertEquals(0, result);
    };

    @Test
    public void emptyString_calculateHighestFrequency_returnsZeroFrequency() {
        int result = testObj
                .calculateHighestFrequency(EMPTY_TEXT_STRING);
        assertEquals(0, result);
    };

    @Test
    public void specialCharacterTextString_calculateHighestFrequency_returnsZeroFrequency() {
        int result = testObj
                .calculateHighestFrequency(SPECIAL_CHARACTER_STRING);
        assertEquals(0, result);
    };

    @Test
    public void existingWordInText_calculateFrequencyForWord_returnsWordFrequency () {
        int result = testObj
                .calculateFrequencyForWord(TEXT_STRING, WORD_STRING);
        assertEquals(3, result);
    };

    @Test
    public void emptyWordString_calculateFrequencyForWord_returnsZeroForWordFrequency () {
        int result = testObj
                .calculateFrequencyForWord(TEXT_STRING, EMPTY_WORD_STRING);
        assertEquals(0, result);
    };

    @Test
    public void emptyTextString_calculateFrequencyForWord_returnsZeroForWordFrequency () {
        int result = testObj
                .calculateFrequencyForWord(EMPTY_TEXT_STRING, WORD_STRING);
        assertEquals(0, result);
    };

    @Test
    public void emptyTextAndWordString_calculateFrequencyForWord_returnsZeroForWordFrequency () {
        int result = testObj
                .calculateFrequencyForWord(EMPTY_TEXT_STRING, EMPTY_WORD_STRING);
        assertEquals(0, result);
    };

    @Test
    public void calculateMostFrequentNWords () {
        List<WordFrequency> result = testObj
                .calculateMostFrequentNWords(TEXT_STRING, 2);

        List<WordFrequency> wordFrequencies = new ArrayList<>();

        WordsFrequencies wordsFrequencies = new WordsFrequencies();
        wordsFrequencies.setWord("the");
        wordsFrequencies.setFrequency(3);
        wordFrequencies.add(wordsFrequencies);

        wordsFrequencies = new WordsFrequencies();
        wordsFrequencies.setWord("a");
        wordsFrequencies.setFrequency(2);
        wordFrequencies.add(wordsFrequencies);

        assertEquals(wordFrequencies.get(0).getWord(), result.get(0).getWord());
        assertEquals(wordFrequencies.get(0).getFrequency(), result.get(0).getFrequency());
        assertEquals(wordFrequencies.get(1).getWord(), result.get(1).getWord());
        assertEquals(wordFrequencies.get(1).getFrequency(), result.get(1).getFrequency());
    }
}
