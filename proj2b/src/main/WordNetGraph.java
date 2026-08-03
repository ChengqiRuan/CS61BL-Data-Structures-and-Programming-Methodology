package main;


import java.util.*;

public class WordNetGraph {

    private Map<Integer, Set<Integer>> adjacencyList;


    public WordNetGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(int curr, int hypo) {
        if (!adjacencyList.containsKey(curr)) {
            adjacencyList.put(curr, new HashSet<>());
        }
        Set<Integer> res = adjacencyList.get(curr);
        res.add(hypo);
    }

    public Set<Integer> getHyposynsetsID(int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            Set<Integer> children = adjacencyList.getOrDefault(current, new HashSet<>());

            for (int hypo: children) {
                if (!visited.contains(hypo)) {
                    visited.add(hypo);
                    queue.add(hypo);
                }

            }
        }

        return visited;
    }


}
