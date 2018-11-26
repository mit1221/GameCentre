package fall2018.csc2017.hangman;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        wordMap.put("Animals",
                "animals.txt");
        wordMap.put("Computer Science Teaching Stream", "teaching_stream.txt");
        wordMap.put("Buildings at the University of Toronto", "uoftbuildings.txt");
    }

    /**
     * @param category the category from which to get the words
     * @return a list of all words for the category
     */
    private static ArrayList<String> getFileWords(String category) {
        ArrayList<String> fileWords = new ArrayList<>();

        // adapted from https://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
        File SDCard = Environment.getExternalStorageDirectory();
        File txt = new File(SDCard, wordMap.get(category));

        try {
            BufferedReader br = new BufferedReader(new FileReader(txt));
            String line;

            while ((line = br.readLine()) != null) {
                fileWords.add(line);
            }
        } catch (IOException e) {
            System.out.print("Issue at getFileWords while trying to read from ");
            System.out.println(wordMap.get(category));
        }
        return fileWords;
    }

    /**
     * @param category the category from which to return the random word.
     * @return a random word from category category
     */
    public static String getWord(String category) {
        ArrayList<String> words = getFileWords(category);
        Random r = new Random();

        int i = r.nextInt(words.size());

        return words.get(i);
    }

    /**
     * @return a Set containing the names of all categories.
     */
    public static Set getCategories() {
        return wordMap.keySet();
    }


}


