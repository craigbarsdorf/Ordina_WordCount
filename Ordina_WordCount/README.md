## The case: counting words.


### Introduction
The task was to implement a part of a text processing library.


### To run the project from the command line:

 - Ensure the Ordina_WordCount project is cloned from GitHub into a folder on your local machine
 - From the command prompt navigate to the "target" folder inside the Ordina_WordCount project
   i.e. Craigs-MacBook-Pro Projects % cd path/to/Ordina_WordCount/target/
 - Then run the command: java -jar  Ordina_WordCount-1.0-SNAPSHOT.jar
   i.e. craigbarsdorf@Craigs-MacBook-Pro target % java -jar Ordina_WordCount-1.0-SNAPSHOT.jar


### To send an HTTP request to the WordCountApplication:

#### From inside a browser enter into the browser search each of the following separately:

   - http://localhost:8080/wordfrequency/highestfrequencyword?text=the,lady,sat,on,the,wall
     - you should get the json:
       {
       "status": 200,
       "responseText": "the,lady,sat,on,the,wall",
       "response": 2
       }

   - http://localhost:8080/wordfrequency/frequencyofword?text=the,lady,sat,on,the,wall&word=the
     - you should get the json:
       {
       "status": 200,
       "responseText": "the,lady,sat,on,the,wall",
       "response": 2
       }

   - http://localhost:8080/wordfrequency/mostfrequentnwords?text=the,lady,sat,on,the,wall,on,a,pillow&n=2
     - you should get the json:
       {
       "status": 200,
       "responseText": "the,lady,sat,on,the,wall,on,a,pillow",
       "response": [
       {
       "word": "on",
       "frequency": 2
       },
       {
       "word": "the",
       "frequency": 2
       }
       ]
       }
   
#### To run curl commands, enter at the command prompt the below commands to send these requests:
     - curl "http://localhost:8080/wordfrequency/highestfrequencyword?text=the,lady,sat,on,the,wall"
     - curl "http://localhost:8080/wordfrequency/frequencyofword?text=the,lady,sat,on,the,wall&word=the"
     - curl "http://localhost:8080/wordfrequency/mostfrequentnwords?text=the,lady,sat,on,the,wall,on,a,pillow&n=2"

   - For the above requests you should get a json back with the following:

     - Craigs-MacBook-Pro Ordina_WordCount % curl "http://localhost:8080/wordfrequency/highestfrequencyword?text=the,lady,sat,on,the,wall"
       {"status":200,"responseText":"the,lady,sat,on,the,wall","response":2}%   

     - Craigs-MacBook-Pro Ordina_WordCount % curl "http://localhost:8080/wordfrequency/frequencyofword?text=the,lady,sat,on,the,wall&word=the"
       {"status":200,"responseText":"the,lady,sat,on,the,wall","response":2}%

     - Craigs-MacBook-Pro Ordina_WordCount % curl "http://localhost:8080/wordfrequency/mostfrequentnwords?text=the,lady,sat,on,the,wall,on,a,pillow&n=2"
       {"status":200,"responseText":"the,lady,sat,on,th*e*,wall,on,a,pillow","response":[{"word":"on","frequency":2},{"word":"the","frequency":2}]}%   


### To run the unit tests:

- Open the Ordina_WordCount project in your IDE
- Then open the unit test classes in the IDE editor
  - nl/ordina/wordcount/controller/WordFrequencyControllerUTest.java
  - nl/ordina/wordcount/service/WordFrequencyAnalyzerImplUTest.java
- Then click on the arrow on the left margin next to the test method and select run