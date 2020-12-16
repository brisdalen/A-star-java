import java.awt.*;
import java.util.Arrays;

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

    private void A_Star(char[][] input) {
        var copy = new char[input.length][];
        for(int i = 0; i < input.length; i++) {
            copy[i] = input[i].clone();
        }

        int len = input.length;
        var startingPos = new Point(2, 2);
        var goalPos = new Point(len-3, 2);

        if (len > 3) {
            copy[startingPos.y][startingPos.x] = 'C';
            copy[goalPos.y][goalPos.x] = 'o';

            for(int i = 0; i < len/2; i++) {
                copy[i][len/2-1] = 'X';
                copy[i][len/2] = 'X';
            }
        }

        display(copy);
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

    public static void main(String[] args) {
        new Main();
    }
}
