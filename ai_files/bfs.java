import java.util.*;

public class bfs {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public void addEdge(int src, int dest) {
        graph.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
        graph.computeIfAbsent(dest, k -> new ArrayList<>());  // ensure node exists
    }

    public void bfs(int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        System.out.print("BFS Traversal: ");

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BFS g = new BFS();
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        g.bfs(0);
    }
}
