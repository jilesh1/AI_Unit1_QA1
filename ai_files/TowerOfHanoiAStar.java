import java.util.*;
  
class TowerOfHanoiAStar   {

    static class State implements Comparable<State> {
        List<Stack<Integer>> pegs;
        List<String> moves; 
        int g; // cost so far
        int h; // heuristic cost

        State(List<Stack<Integer>> pegs, List<String> moves, int g, int h) {
            this.pegs = new ArrayList<>();
            for (Stack<Integer> peg : pegs) {
                this.pegs.add((Stack<Integer>) peg.clone());
            }
            this.moves = new ArrayList<>(moves);
            this.g = g;
            this.h = h;
        }

        int f() {
            return g + h;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.f(), other.f());
        }
    }

    public static void solveAStar(int n) {
        Stack<Integer> source = new Stack<>();
        Stack<Integer> target = new Stack<>();
        Stack<Integer> auxiliary = new Stack<>();

        // Initialize source peg
        for (int i = n; i >= 1; i--) source.push(i);

        List<Stack<Integer>> startPegs = Arrays.asList(source, auxiliary, target);
        State startState = new State(startPegs, new ArrayList<>(), 0, heuristic(startPegs, n));

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(startState);

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            State current = pq.poll();
            String key = generateKey(current.pegs);

            if (visited.contains(key)) continue;
            visited.add(key);

            // Goal check: all disks on target peg (peg 2)
            if (current.pegs.get(2).size() == n) {
                System.out.println("A* sequence of moves:");
                for (String move : current.moves) {
                    System.out.println(move);
                }
                return;
            }

            // Try all possible moves between pegs
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i != j && !current.pegs.get(i).isEmpty() &&
                        (current.pegs.get(j).isEmpty() || current.pegs.get(i).peek() < current.pegs.get(j).peek())) {

                        List<Stack<Integer>> newPegs = clonePegs(current.pegs);
                        int disk = newPegs.get(i).pop();
                        newPegs.get(j).push(disk);

                        List<String> newMoves = new ArrayList<>(current.moves);
                        newMoves.add("Move disk " + disk + " from peg " + (i+1) + " to peg " + (j+1));

                        int newG = current.g + 1;
                        int newH = heuristic(newPegs, n);

                        pq.add(new State(newPegs, newMoves, newG, newH));
                    }
                }
            }
        }
    }

    // Heuristic: number of disks not on target peg
    private static int heuristic(List<Stack<Integer>> pegs, int n) {
        return n - pegs.get(2).size();
    }

    private static List<Stack<Integer>> clonePegs(List<Stack<Integer>> pegs) {
        List<Stack<Integer>> newPegs = new ArrayList<>();
        for (Stack<Integer> peg : pegs) {
            newPegs.add((Stack<Integer>) peg.clone());
        }
        return newPegs;
    }

    private static String generateKey(List<Stack<Integer>> pegs) {
        StringBuilder sb = new StringBuilder();
        for (Stack<Integer> peg : pegs) {
            sb.append(peg.toString()).append("|");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int n = 3;
        solveAStar(n);
    }
}
