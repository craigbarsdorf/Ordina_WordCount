package nl.ordina.wordcount.dto;


import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class WordsFrequencies implements WordFrequency, Serializable {

    private String word;
    private int frequency;

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    };

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    };
}
