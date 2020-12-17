import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Main {

    public Main() {
        int size = 16;

        var grid = new char[size][size];

        for(var ca : grid) {
            Arrays.fill(ca, '.');
        }

        for(int i = 0; i < grid.length; i++) {
            grid[i][0] = 'X'; // 'X' = obstacle
            grid[i][grid.length-1] = 'X';
            grid[0][i] = 'X';
            grid[grid.length-1][i] = 'X';
        }
        A_Star(grid);
    }

    /*
    What A* Search Algorithm does is that at each step it picks the node according to a value-‘f’
    which is a parameter equal to the sum of two other parameters – ‘g’ and ‘h’.
    At each step it picks the node/cell having the lowest ‘f’, and process that node/cell.

    g = the movement cost to move from the starting point to a given square on the grid,
    following the path generated to get there.

    h = the estimated movement cost to move from that given square on the grid to the final destination.
    This is often referred to as the heuristic, which is nothing but a kind of smart guess.
    We really don’t know the actual distance until we find the path, because all sorts of things can be in the
    way (walls, water, etc.).
    There can be many ways to calculate this ‘h’ which are discussed in the later sections.
     */
    private void A_Star(char[][] input) {
        var copy = new char[input.length][];
        for(int i = 0; i < input.length; i++) {
            copy[i] = input[i].clone();
        }

        int len = input.length;
        var startingPos = new NavigationPoint(2, 2);
        var goalPos = new NavigationPoint(len-3, 2);

        if (len > 3) {
            copy[startingPos.position.y][startingPos.position.x] = 'C';
            copy[goalPos.position.y][goalPos.position.x] = 'o';

            for(int i = 0; i < len/2+4; i++) {
                copy[i][len/2-1] = 'X';
                copy[i][len/2] = 'X';
            }
        }

        display(copy);

        var closedSet = new HashSet<NavigationPoint>();
        PriorityQueue<NavigationPoint> openSet = new PriorityQueue<>(new NavigationPointComparator()); // TODO: override Comparator
        openSet.add(startingPos);

        while(!openSet.isEmpty() && !startingPos.equals(goalPos)) {
            var q = openSet.poll();
        }

    }

    private NavigationPoint[] getSurroundingNodes(NavigationPoint centre, char[][] input) {
        
    }

    public void display(char[][] grid) {
        for(var ca : grid) {
            var builder = new StringBuilder();
            for(var c : ca) {
                builder.append(c == 'X' ? "X" : c == 'C' ? "C" : String.valueOf(c)).append(" ");
            }
            System.out.println(builder.toString());
        }
    }

    class NavigationPoint {
        private NavigationPoint parent;
        private Point position;
        private double f, g, h;

        public NavigationPoint(int x, int y) {
            this(null, x, y);
        }

        public NavigationPoint(NavigationPoint parent, int x, int y) {
            this.parent = parent;
            this.position = new Point(x, y);
            f = 0;
            g = 0;
            h = 0;
        }

        public Point getPosition() {
            return position;
        }

        public void setG(double g) {
            this.g = g;
            f = g + h;
        }

        public void setH(double h) {
            this.h = h;
            f = g + h;
        }

        public double getF() {
            return f;
        }

        public double getG() {
            return g;
        }

        public double getH() {
            return h;
        }
    }

    class NavigationPointComparator implements Comparator<NavigationPoint> {
        @Override
        public int compare(NavigationPoint o1, NavigationPoint o2) {
            return o1.f - o2.f < 0 ? -1 : o1.f == o2.f ? 0 : 1;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
