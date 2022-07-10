package nl.ordina.wordcount;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("nl.ordina")
public class WordCountApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordCountApplication.class, args);
    }
}
