import java.util.ArrayList;
import java.util.List;

public class TowerOfHanoiDFS {

    static List<String> moves = new ArrayList<>();

    public static void solveHanoi(int n, char source, char target, char auxiliary) {
        if (n == 1) {
            moves.add("Move disk 1 from " + source + " to " + target);
            return;
        }
        // Move n-1 disks from source to auxiliary
        solveHanoi(n - 1, source, auxiliary, target);
        // Move nth disk from source to target
        moves.add("Move disk " + n + " from " + source + " to " + target);
        // Move n-1 disks from auxiliary to target
        solveHanoi(n - 1, auxiliary, target, source);
    }

    public static void main(String[] args) {
        int n = 3; // Number of disks
        solveHanoi(n, 'A', 'C', 'B');
        System.out.println("DFS sequence of moves:");
        for (String move : moves) {
            System.out.println(move);
        }
    }
}
