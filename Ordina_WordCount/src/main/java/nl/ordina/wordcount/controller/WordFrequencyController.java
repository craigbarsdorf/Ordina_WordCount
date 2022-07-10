package nl.ordina.wordcount.controller;

import nl.ordina.wordcount.dto.WordFrequency;
import nl.ordina.wordcount.web.Response;
import nl.ordina.wordcount.service.WordFrequencyAnalyzerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wordfrequency")
public class WordFrequencyController {

    @Autowired
    private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    @RequestMapping("/highestfrequencyword")
    public ResponseEntity<Response<Object>> getHighestFrequencyWord(@RequestParam String text) {

        int highestFrequency = wordFrequencyAnalyzer.calculateHighestFrequency(text);

        return createResponse(highestFrequency, text);
    }

    @RequestMapping("/frequencyofword")
    public ResponseEntity<Response<Object>> getFrequencyForWord(@RequestParam String text, @RequestParam String word) {

        int highestFrequency = wordFrequencyAnalyzer.calculateFrequencyForWord(text, word);

        return createResponse(highestFrequency, text);
    }

    @RequestMapping("/mostfrequentnwords")
    public ResponseEntity<Response<Object>> getMostFrequentNWords(@RequestParam String text, @RequestParam int n) {

        List<WordFrequency> highestFrequency = wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);

        return createResponse(highestFrequency, text);
    }

    // A helper method just to construct the response for each of the controllers
    private ResponseEntity<Response<Object>> createResponse(Object highestFrequency, String text) {

        // Build the response
        Response<Object> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setResponse(highestFrequency);
        response.setResponseText(text);

        // Send the response back to the controller to then send out the response
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
