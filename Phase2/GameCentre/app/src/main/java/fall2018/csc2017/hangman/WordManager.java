package fall2018.csc2017.hangman;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
        // new word dictionaries must be stored as txt in assets.
        // https://gist.github.com/atduskgreg/3cf8ef48cb0d29cf151bedad81553a54 <-- animals
        wordMap.put("Animals",
                "animals.txt");
        wordMap.put("Instructors", "...");
    }

    /**
     *
     * @param category the category from which to get the words
     * @param c
     * @return a list of all words for the category
     */
    private static ArrayList getFileWords(String category, Context c) {
        AssetManager assetManager = c.getAssets();
        ArrayList<String> a = new ArrayList<>();

        try {
            // https://teamtreehouse.com/community/how-to-read-from-a-text-file-which-is-in-the-same-folder-as-the-java-file-in-an-complex-andriod-studio
            // https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html#read-char:A-int-int-
            InputStream i = assetManager.open(category);
            BufferedReader br = new BufferedReader(new InputStreamReader(i));
            String line = br.readLine();
            while (line != null) {
                a.add(line);
                line = br.readLine();

            }
            return a;
        } catch (IOException e) {
            System.out.println("file not found");
            return null;
        }
    }

    /**
     * @param category the category from which to return the random word.
     * @param c
     * @return a random word from category category
     */
    public static String getWord(String category, Context c) {
        ArrayList<String> words = getFileWords(category, c);
        Random r = new Random();

        int i = r.nextInt(words.size());

        return words.get(i);
    }

    /**
     *
     * @return a Set containing the names of all categories.
     */
    public static Set getCategories() {
        return wordMap.keySet();
    }


}
