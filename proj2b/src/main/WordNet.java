package main;
import edu.princeton.cs.algs4.In;

import java.util.*;


public class WordNet {
    private WordNetGraph graph;
    private HashMap<Integer, Set<String>> idToString;
    private HashMap<String, Set<Integer>> stringToId;
    private HashMap<String, Double> stringToDouble;

    public WordNet(String synsetPath, String hypoPath) {
        graph = new WordNetGraph();
        idToString = new HashMap<>();
        stringToId = new HashMap<>();
        synsets(synsetPath);
        hyponyms(hypoPath);
    }


    //第一个是数字，第二个是词

    private void hyponyms(String hypoPath) {
        /*try (Scanner s1 = new Scanner(new File(hypoPath))) {
            while (s1.hasNextLine()) {
                String line = s1.nextLine();
                String[] parts = line.split(","); //用，隔开
                int curr = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    int hypoid = Integer.parseInt(parts[i]);
                    graph.addEdge(curr, hypoid);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         */
        In in = new In(hypoPath);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] parts = line.split(","); //用，隔开
            int curr = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int hypoid = Integer.parseInt(parts[i]);
                graph.addEdge(curr, hypoid);
            }
        }
    }



    //第一个是curr，后面是hypo
    private void synsets(String synsetPath) {
        /*
        try (Scanner s = new Scanner(new File(synsetPath))) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String[] words = parts[1].split(" ");
                Set<String> wordlst = new HashSet<>();
                for (String w : words) {
                    wordlst.add(w);
                    stringToId.computeIfAbsent(w, k -> new HashSet<>()).add(id);
                }
                idToString.put(id, wordlst);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         */
        In in = new In(synsetPath);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");
            Set<String> wordlst = new HashSet<>();
            for (String w : words) {
                wordlst.add(w);
                stringToId.computeIfAbsent(w, k -> new HashSet<>()).add(id);
            }
            idToString.put(id, wordlst);
        }
    }


    public Set<String> getAllHyponyms(String word) {
        Set<String> allWords = new TreeSet<>();
        if (!stringToId.containsKey(word)) {
            return new TreeSet<>();
        }
        Set<Integer> synsets = stringToId.get(word);
        for (int i : synsets) {
            Set<Integer> allHypoid = graph.getHyposynsetsID(i);
            for (int hypoid : allHypoid) {
                Set<String> wordsInSyn = idToString.get(hypoid);
                allWords.addAll(wordsInSyn);
            }
        }
        return  allWords;
    }

    public Set<String> getCommonHyponyms(List<String> wordList) {
        if (wordList.isEmpty()) {
            return new TreeSet<>();
        }
        // 取第一个词的全部下位词作为初始交集
        Set<String> intersection = getAllHyponyms(wordList.get(0));
        // 依次和剩余单词求交集
        for (int i = 1; i < wordList.size(); i++) {
            Set<String> otherWords = getAllHyponyms(wordList.get(i));
            intersection.retainAll(otherWords);
            if (intersection.isEmpty()) {
                break; // 提前终止，无交集
            }
        }
        return intersection;
    }


}
