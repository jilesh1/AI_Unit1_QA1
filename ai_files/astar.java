import java.util.*;

public class astar {
    static class Node implements Comparable<Node> {
        int vertex;
        int gCost;   // distance from start
        int fCost;   // g + h

        Node(int v, int g, int f) {
            vertex = v;
            gCost = g;
            fCost = f;
        }

        public int compareTo(Node other) {
            return Integer.compare(this.fCost, other.fCost);
        }
    }

    private Map<Integer, List<int[]>> graph = new HashMap<>();
    private Map<Integer, Integer> heuristic = new HashMap<>();

    // add edge with weight
    public void addEdge(int src, int dest, int weight) {
        graph.computeIfAbsent(src, k -> new ArrayList<>()).add(new int[]{dest, weight});
        graph.computeIfAbsent(dest, k -> new ArrayList<>()); // ensure node exists
    }

    public void setHeuristic(int vertex, int h) {
        heuristic.put(vertex, h);
    }

    public List<Integer> aStar(int start, int goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Integer, Integer> gCost = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        for (int v : graph.keySet()) {
            gCost.put(v, Integer.MAX_VALUE);
        }

        gCost.put(start, 0);
        pq.add(new Node(start, 0, heuristic.get(start)));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.vertex == goal) {
                return buildPath(parent, goal);
            }

            for (int[] edge : graph.get(current.vertex)) {
                int neighbor = edge[0];
                int weight = edge[1];

                int newG = current.gCost + weight;

                if (newG < gCost.get(neighbor)) {
                    gCost.put(neighbor, newG);
                    parent.put(neighbor, current.vertex);

                    int f = newG + heuristic.get(neighbor);
                    pq.add(new Node(neighbor, newG, f));
                }
            }
        }
        return Collections.emptyList(); // no path
    }

    private List<Integer> buildPath(Map<Integer, Integer> parent, int goal) {
        List<Integer> path = new ArrayList<>();
        int cur = goal;

        while (parent.containsKey(cur)) {
            path.add(cur);
            cur = parent.get(cur);
        }

        path.add(cur); 
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        AStar g = new AStar();

        // edges: src, dest, weight
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 10);

        // heuristic values
        g.setHeuristic(0, 7);
        g.setHeuristic(1, 6);
        g.setHeuristic(2, 4);
        g.setHeuristic(3, 0);

        System.out.println("A* Path: " + g.aStar(0, 3));
    }
}
