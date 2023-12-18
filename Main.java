import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class TrieNode { //In Trie data stucture a node
    TrieNode[] child = new TrieNode[26];
    boolean isEnd;
}

public class Main {

    /*returns new Trie node where isend is set to false,
    * Also all the child node are set to NULL.
    */
    static TrieNode createTrieNode() {
        TrieNode node = new TrieNode();
        node.isEnd = false;
        for (int i = 0; i < 26; i++) {
            node.child[i] = null;
        }
        return node;
    }

    //Insert a sting into the data stucture.
    static void insertIntoTrie(TrieNode root, String st) {
        TrieNode node = root;
        for (int i = 0; i < st.length(); i++) {
            int index = st.charAt(i) - 'a';
            if (node.child[index] == null) {
                node.child[index] = createTrieNode();
            }
            node = node.child[index];
        }
        node.isEnd = true;
    }

    //Helps to Search a particular sting in the data stucture.
    static boolean searchInTrie(TrieNode root, String st) {
        TrieNode node = root;
        for (int i = 0; i < st.length(); i++) {
            int index = st.charAt(i) - 'a';
            if (node.child[index] == null) {
                return false;
            }
            node = node.child[index];
        }
        return (node != null && node.isEnd);
    }
    //Reads the contents of a file and adds each non-empty line to the given ArrayList.
    static void readAndAddFileContent(String fileName, ArrayList<String> list) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * checks if the given sting can be segmented into space-separated
     * sequence(s) of dictionary words using the given TrieNode as the dictionary Checks that Recursively
     */
    static boolean checkSegmentation(String st, TrieNode root, int length) {
        int size = st.length();
        if (size == 0 && length != 1) {
            return true;
        }
        for (int i = 1; i <= size; i++) {
            if (searchInTrie(root, st.substring(0, i)) && checkSegmentation(st.substring(i), root, length + 1)) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * Finds the longest sting in the given ArrayList and returns it.
     * The longest sting is removed from the ArrayList.
     */
    static String findLongest(ArrayList<String> list) {
        String result = "";
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (result.length() < list.get(i).length()) {
                index = i;
                result = list.get(i);
            }
        }
        list.set(index, "");
        return result;
    }

    /*
     * Finds the longest and second longest compound words from a given list of words
     * Using Trie Data stuctue.
     */
    static void findCompoundWords(ArrayList<String> list) {
        TrieNode root = createTrieNode();
        for (String s : list) {
            insertIntoTrie(root, s);
        }
        int count = 2;
        while (count > 0) {
            String st = findLongest(list);
            if (checkSegmentation(st, root, 0)) {
                if (count == 2) {
                    System.out.println("Longest Compound Word: " + st);
                } else {
                    System.out.println("Second Largest Compound Word: " + st);
                }
                count--;
            }
        }
    }

    /*
     * Main Method of the program helps to read both the files and print
     * time taken to execute the program in millisec.
     */
    public static void main(String[] args) {
        long st = System.nanoTime();
        System.out.println("FOR INPUT 01:");
        ArrayList<String> wordList = new ArrayList<>();
        readAndAddFileContent("Input_01.txt", wordList);
        findCompoundWords(wordList);
        long et= System.nanoTime();
        printElapsedTime(st, et); // st=StartTime , et=EndTime
        System.out.println("FOR INPUT 02:");
        long st2 = System.nanoTime();
        ArrayList<String> wordList2 = new ArrayList<>();
        readAndAddFileContent("Input_02.txt", wordList2);
        findCompoundWords(wordList2);
        long et2 = System.nanoTime();
        printElapsedTime(st2, et2);
    }

    static void printElapsedTime(long st, long et) {
        long elapsedTimeMillis = (et- st) / 1_000_000;  //to calculate total time in milliseconds
        System.out.println("Time taken to process the file: " + elapsedTimeMillis + " milliseconds");
    }
}