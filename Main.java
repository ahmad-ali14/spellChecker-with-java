import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.JFileChooser;

class Main {

    private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String args[]) {
        File file = new File("./english.txt");
        Scanner scn = new Scanner("");
        scn.close();

        HashSet<String> englishDictionarySet = new HashSet<String>(); // set of all words in english generated from the
        // supplied file: words.txt

        try {
            scn = new Scanner(file);
            scn.useDelimiter("[^a-zA-Z]+");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scn.hasNext()) {
            String tk = scn.next();
            englishDictionarySet.add(tk.toLowerCase()); // add all words in the words.txt to the dictionary hashSet.
        }

        System.out.println("dictionary size:  " + englishDictionarySet.size());
        System.out.println();

        File usrFile = getInputFileNameFromUser();
        Scanner usrScanner = new Scanner("");
        usrScanner.close();

        try {
            usrScanner = new Scanner(usrFile);
            usrScanner.useDelimiter("[^a-zA-Z]+");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (usrScanner.hasNext()) {
            String tk = usrScanner.next().toLowerCase();
            boolean inSet = englishDictionarySet.contains(new String(tk)); // check if word or token is in the
                                                                           // englishDictionarySet,
            // if not then it is misspelled
            if (inSet == true) {
                System.out.println(tk + ": (no suggestions) "); // don't search for suggestion if the word is not
                                                                // misspelled
            } else {
                TreeSet<String> suggestions = corrections(tk, englishDictionarySet); // get TreeSet of suggestions.
                // get an array of TreeSet, just for easier printing, you can ignore this step
                // but you need to change the next step abit
                String[] suggestionsArray = suggestions.toArray(new String[suggestions.size()]);
                if (suggestionsArray.length == 0) { // if no suggestions found
                    System.out.println(tk + ": (no suggestions) ");
                } else { // print out suggestions
                    System.out.println(tk + ": " + Arrays.toString(suggestionsArray));
                }
            }
        }

    }

    /**
     * Lets the user select an input file using a standard file selection dialog
     * box. If the user cancels the dialog without selecting a file, the return
     * value is null.
     */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();
    }

    /***
     * Corrections wil try various possibilities on the supplied word, then it will
     * check against the supplied dictionary, then return an array of suggested
     * corrections.
     * 
     * @param badWord    String _ a misspelled word.
     * @param dictionary HashSet _ set of all words in english
     * @return
     */
    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary) {
        TreeSet<String> result = new TreeSet<String>();

        /***
         * My approach is to get possible words after doing each of the 5 operations
         * suggested in the exercise then check them against the englishDictionarySet
         * and if a possible variations is in the englishDictionarySet then it is a
         * valid suggestion.
         */

        // Delete any one of the letters from the misspelled word.
        String[] deleteVariations = getDeletingLetterVariations(badWord);
        for (int i = 0; i < deleteVariations.length; i++) {
            if (dictionary.contains(deleteVariations[i])) {
                result.add(deleteVariations[i]);
            }
        }

        // • Change any letter in the misspelled word to any other letter.
        String[] changeVariations = getChangingLetterVariations(badWord);
        for (int i = 0; i < changeVariations.length; i++) {
            if (dictionary.contains(changeVariations[i])) {
                result.add(changeVariations[i]);
            }
        }

        // • Insert any letter at any point in the misspelled word.
        String[] insertingVariations = getInsertingLetterVariations(badWord);
        for (int i = 0; i < insertingVariations.length; i++) {
            if (dictionary.contains(insertingVariations[i])) {
                result.add(insertingVariations[i]);
            }
        }
        // • Swap any two neighboring characters in the misspelled word.
        String[] swappingVariations = getSwappingLetterVariations(badWord);
        for (int i = 0; i < swappingVariations.length; i++) {
            if (dictionary.contains(swappingVariations[i])) {
                result.add(swappingVariations[i]);
            }
        }
        // • Insert a space at any point in the misspelled word (and check that both of
        // the words that are produced are in the dictionary)
        String[][] insertingSpaceVariations = getInsertingSpaceVariations(badWord);
        for (int i = 0; i < insertingSpaceVariations.length; i++) {
            String w1 = insertingSpaceVariations[i][0];
            String w2 = insertingSpaceVariations[i][1];
            if (dictionary.contains(w1) && dictionary.contains(w2)) {
                result.add(w1);
                result.add(w2);
            }

        }
        return result;
    };

    /**
     * Delete any one of the letters from the misspelled word.
     * 
     * @param word
     * @return array of words after doing the deletions
     */
    static String[] getDeletingLetterVariations(String word) {
        String[] arr = new String[word.length()];

        for (int i = 0; i < word.length(); i++) {
            String sub = word.substring(0, i) + word.substring(i + 1);
            arr[i] = sub;
        }

        return arr;
    }

    /**
     * Change any letter in the misspelled word to any other letter.
     * 
     * @param word
     * @return array of words after doing the letter changing
     */
    static String[] getChangingLetterVariations(String word) {
        ArrayList<String> arr = new ArrayList<String>();

        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < alphabet.length; j++) {
                String sub = word.substring(0, i) + alphabet[j] + word.substring(i + 1);
                arr.add(sub);
            }
        }

        return arr.toArray(new String[arr.size()]);
    }

    /***
     * Insert any letter at any point in the misspelled word.
     * 
     * @param word
     * @return array of words after doing the insertions
     */
    static String[] getInsertingLetterVariations(String word) {
        ArrayList<String> arr = new ArrayList<String>();

        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < alphabet.length; j++) {
                String sub = word.substring(0, i) + alphabet[j] + word.substring(i);
                arr.add(sub);
            }
        }

        return arr.toArray(new String[arr.size()]);
    }

    /***
     * Swap any two neighboring characters in the misspelled word.
     * 
     * @param word
     * @return array of words after doing the swapping
     */
    static String[] getSwappingLetterVariations(String word) {
        ArrayList<String> arr = new ArrayList<String>();

        for (int i = 0; i < word.length() - 1; i++) {
            String sub = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 1);
            arr.add(sub);
        }

        return arr.toArray(new String[arr.size()]);
    }

    /***
     * Insert a space at any point in the misspelled word (and check that both of //
     * the words that are produced are in the dictionary)
     * 
     * @param word
     * @return array of words after doing the space insertion.
     */
    static String[][] getInsertingSpaceVariations(String word) {
        ArrayList<String[]> arrayList = new ArrayList<String[]>();

        for (int i = 0; i < word.length(); i++) {
            String sub = word.substring(0, i) + " " + word.substring(i);
            String[] parts = sub.split(" ");
            arrayList.add(parts);
        }

        String[][] array = new String[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            String[] row = arrayList.get(i);
            array[i] = row;
        }
        return array;

    }

}
