import java.util.*;

public class dfs {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public void addEdge(int src, int dest) {
        graph.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
        graph.computeIfAbsent(dest, k -> new ArrayList<>());   // ensure node exists
    }

    public void dfs(int start) {
        Set<Integer> visited = new HashSet<>();
        System.out.print("DFS Traversal: ");
        dfsUtil(start, visited);
        System.out.println();
    }

    private void dfsUtil(int node, Set<Integer> visited) {
        visited.add(node);
        System.out.print(node + " ");

        for (int neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        DFS g = new DFS();
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        g.dfs(0);
    }
}
