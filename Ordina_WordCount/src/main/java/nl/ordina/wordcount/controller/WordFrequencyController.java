package nl.ordina.wordcount.controller;

import nl.ordina.wordcount.dto.WordFrequency;
import nl.ordina.wordcount.service.WordFrequencyAnalyzer;
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
    private WordFrequencyAnalyzer wordFrequencyAnalyzer;

    @RequestMapping("/highestfrequencyword")
    public ResponseEntity<Response<Object>> getHighestFrequencyWord(@RequestParam String text) {

        int highestFrequency = wordFrequencyAnalyzer.calculateHighestFrequency(text);

        return createResponse(highestFrequency, text,HttpStatus.OK);
    }

    @RequestMapping("/frequencyofword")
    public ResponseEntity<Response<Object>> getFrequencyForWord(@RequestParam String text, @RequestParam String word) {

        int highestFrequency = 0;

        if (!word.matches("^[a-zA-Z]+$")) {
            return createResponse(highestFrequency, text, HttpStatus.BAD_REQUEST);
        }

        highestFrequency = wordFrequencyAnalyzer.calculateFrequencyForWord(text, word);

        return createResponse(highestFrequency, text, HttpStatus.OK);
    }

    @RequestMapping("/mostfrequentnwords")
    public ResponseEntity<Response<Object>> getMostFrequentNWords(@RequestParam String text, @RequestParam int n) {

        List<WordFrequency> highestFrequency = wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);

        HttpStatus httpStatus = HttpStatus.OK;
        if (highestFrequency == null || highestFrequency.isEmpty()) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return createResponse(highestFrequency, text, httpStatus);
    }

    // A helper method just to construct the response for each of the controllers
    private ResponseEntity<Response<Object>> createResponse(Object highestFrequency, String text, HttpStatus httpStatus) {

        // Build the response
        Response<Object> response = new Response<>();
        response.setStatus(httpStatus.value());
        response.setResponse(highestFrequency);
        response.setResponseText(text);

        // Send the response back to the controller to then send out the response
        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }
}
