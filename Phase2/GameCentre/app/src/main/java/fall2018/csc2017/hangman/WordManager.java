package fall2018.csc2017.hangman;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public abstract class WordManager {
    /**
     * Maps names of categories to the names of their respective text files.
     * (category name, file name)
     */
    private static HashMap<String, String> wordMap = new HashMap<>();

    static { // https://www.dummies.com/programming/java/how-to-use-static-initializers-in-java/

        // https://gist.github.com/atduskgreg/3cf8ef48cb0d29cf151bedad81553a54 <-- animals
        // new text files must be stored in assets
        wordMap.put("Animals",
                "animals.txt");
        wordMap.put("Computer Science Teaching Stream", "teaching_stream.txt");
        wordMap.put("Buildings at the University of Toronto", "uoftbuildings.txt");
    }

    /**
     * @param category the category from which to get the words
     * @return a list of all words for the category
     */
    public static ArrayList<String> getFileWords(String category, Context c) { // need contexzt now
        ArrayList<String> fileWords = new ArrayList<>();

        // adapted from https://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
        // and https://stackoverflow.com/questions/9544737/read-file-from-assets

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getAssets().open(wordMap.get(category))));
            String line;

            while ((line = br.readLine()) != null) {
                fileWords.add(line);
                System.out.println(fileWords);
            }
        } catch (IOException e) {
            System.out.print("Issue at getFileWords while trying to read from ");
            System.out.println(wordMap.get(category));
        }

        return fileWords;
    }

    /**
     * @param wordList the list from which the element is returned
     * @return a random element(word) from wordList
     */
    public static String randWordFromList(ArrayList<String> wordList) {

        Random r = new Random();

        int i = r.nextInt(wordList.size());
        System.out.println(i);
        return wordList.get(i);
    }

    /**
     * @param category the category from which to get the random word
     * @return a random word from category category
     */
    public static String getWord(String category, Context c) {
        ArrayList<String> words = getFileWords(category, c);
        return randWordFromList(words);

    }

    /**
     * @return a Set containing the names of all categories.
     */
    public static Set getCategories() {
        return wordMap.keySet();
    }

}


