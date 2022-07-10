package nl.ordina.wordcount.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@WebMvcTest(WordFrequencyController.class)
@ComponentScan("nl.ordina")
public class WordFrequencyControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHighestFrequencyWord() throws Exception {

        MultiValueMap<String, String> paraMap =new LinkedMultiValueMap<>();
        paraMap.add("text", "the the");

        mockMvc.perform(get("/wordfrequency/highestfrequencyword").params(paraMap))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":200,\"responseText\":\"the the\",\"response\":2}"));
    }

    @Test
    public void testGetFrequencyForWord() throws Exception {

        MultiValueMap<String, String> paraMap =new LinkedMultiValueMap<>();
        paraMap.add("text", "the the");
        paraMap.add("word", "the");

        mockMvc.perform(get("/wordfrequency/frequencyofword").params(paraMap))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":200,\"responseText\":\"the the\",\"response\":2}"));
    }

    @Test
    public void testGetMostFrequentNWords() throws Exception {

        MultiValueMap<String, String> paraMap =new LinkedMultiValueMap<>();
        paraMap.add("text", "on the table is the cup sitting on");
        paraMap.add("n", "2");

        mockMvc.perform(get("/wordfrequency/mostfrequentnwords").params(paraMap))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":200,\"responseText\":\"on the table is the cup sitting on\",\"response\":[{\"word\":\"on\",\"frequency\":2},{\"word\":\"the\",\"frequency\":2}]}"));
    }
}
