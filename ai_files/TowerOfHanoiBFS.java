import java.util.*;

class TowerOfHanoiBFS {

    static class State {
        List<Stack<Integer>> pegs;
        List<String> moves;

        State(List<Stack<Integer>> pegs, List<String> moves) {
            this.pegs = new ArrayList<>();
            for (Stack<Integer> peg : pegs) {
                this.pegs.add((Stack<Integer>) peg.clone());
            }
            this.moves = new ArrayList<>(moves);
        }
    }

    public static void solveBFS(int n) {
        Stack<Integer> source = new Stack<>();
        Stack<Integer> target = new Stack<>();
        Stack<Integer> auxiliary = new Stack<>();

        // Initialize source peg with disks (largest at bottom)
        for (int i = n; i >= 1; i--) source.push(i);

        List<Stack<Integer>> startPegs = Arrays.asList(source, auxiliary, target);
        State startState = new State(startPegs, new ArrayList<>());

        Queue<State> queue = new LinkedList<>();
        queue.add(startState);

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            State current = queue.poll();
            String stateKey = generateKey(current.pegs);

            if (visited.contains(stateKey)) continue;
            visited.add(stateKey);

            // Check goal: all disks on target peg (peg 2)
            if (current.pegs.get(2).size() == n) {
                System.out.println("BFS sequence of moves:");
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

                        // Move top disk from peg i to peg j
                        List<Stack<Integer>> newPegs = clonePegs(current.pegs);
                        int disk = newPegs.get(i).pop();
                        newPegs.get(j).push(disk);

                        List<String> newMoves = new ArrayList<>(current.moves);
                        newMoves.add("Move disk " + disk + " from peg " + (i+1) + " to peg " + (j+1));

                        queue.add(new State(newPegs, newMoves));
                    }
                }
            }
        }
    }

    private static List<Stack<Integer>> clonePegs(List<Stack<Integer>> pegs) {
        List<Stack<Integer>> newPegs = new ArrayList<>();
        for (Stack<Integer> peg : pegs) {
            newPegs.add((Stack<Integer>) peg.clone());
        }
        return newPegs;
    }

    private static String generateKey(List<Stack<Integer>> pegs) {
        StringBuilder key = new StringBuilder();
        for (Stack<Integer> peg : pegs) {
            key.append(peg.toString()).append("|");
        }
        return key.toString();
    }

    public static void main(String[] args) {
        int n = 3;
        solveBFS(n);
    }
}
