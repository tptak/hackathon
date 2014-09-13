package letters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by pc on 2014-09-13.
 */
public class LetterProducer {


    private List<Character> characterList = new ArrayList<Character>();
    private int letterCount = 0;

    public LetterProducer(InputStream dictionary) throws IOException {
        initList(dictionary);
    }

    private void initList(InputStream dictionary) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> letterMap = mapper.readValue(dictionary, Map.class);
        for(Map.Entry<String, List<String>> entry: letterMap.entrySet()) {
            addLetters(entry.getValue(), Integer.parseInt(entry.getKey()));
        }
    }

    private void addLetters(List<String> letters, int num) {
        for (String letter : letters) {
            for (int i = 0; i < num; i++) {
                characterList.add(letter.charAt(0));
            }
            letterCount += num;
        }
    }

    public Character nextLetter() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(letterCount);
        return characterList.get(randomInt);
    }
}
