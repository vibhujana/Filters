import javax.swing.*;
import java.awt.*;
import java.security.AlgorithmConstraints;

public class Game extends Canvas {
    private static final int ALIVE = 1;
    private static final int DEAD = 0;
    public static int[] nextToLocsRow = {1, -1, 0, 0, 1, -1, 1, -1};
    public static int[] nextToLocsCol = {0, 0, 1, -1, 1, -1, -1, 1};
    public static int[] aliveOrDead = {DEAD, DEAD, ALIVE, ALIVE, DEAD, DEAD, DEAD, DEAD, DEAD};
    private static int[][] board = new int[30][50];
    private static int[][] newBoard = new int[board.length][board[0].length];
    private static int[][] oldBoard = new int[board.length][board[0].length];
    private static final int l = 10;
    private static final int canvasLength = l * board.length;
    private static final int canvasHeight = l * board[0].length;
    private static JFrame frame = new JFrame("My Drawing");
    private static Canvas canvas = new Game();


    public static void main(String[] args) {
        canvas.setSize(canvasHeight, canvasLength);

        gameOfLife(board);

    }


    private static void gameOfLife(int[][] board) {
        randomizeBoard(board);
//        glider(board);

        oneGeneration(board);
        int count = 0;

        while (!compare(board, oldBoard)) {
            oneGeneration(board);
            count++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static void oneGeneration(int[][] board) {
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                int neighbourCount = 0;
                for (int k = 0; k < nextToLocsRow.length; k++) {
                    if (board[i + nextToLocsRow[k]][j + nextToLocsCol[k]] == ALIVE) {

                        neighbourCount++;
                    }
                }
                newBoard[i][j] = DEAD;

                if (aliveOrDead[neighbourCount] == ALIVE && board[i][j] == ALIVE) {

                    newBoard[i][j] = ALIVE;
                }
                if (board[i][j] == DEAD && neighbourCount == 3) {

                    newBoard[i][j] = ALIVE;
                }


            }

        }

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                oldBoard[i][j] = board[i][j];
                board[i][j] = newBoard[i][j];
            }

        }

        draw();


    }

    private static void randomizeBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = (int) (Math.random() * 2);


            }
        }

    }

    private static void glider(int[][] board) {
        board[2][1] = ALIVE;
        board[3][2] = ALIVE;
        board[1][3] = ALIVE;
        board[2][3] = ALIVE;
        board[3][3] = ALIVE;
    }

    private static boolean compare(int[][] board, int[][] newBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != newBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void draw() {
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {

        int x = 0;
        int y = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                g.setColor(board[i][j] == 0 ? Color.WHITE : Color.BLACK);
                g.fillRect(j * l, i * l, l, l);
            }

        }
    }


}
