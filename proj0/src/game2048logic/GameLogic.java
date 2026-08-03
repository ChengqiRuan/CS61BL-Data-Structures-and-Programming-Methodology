package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return minR.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        if (r == minR) {
            return minR;
        }
        int goal = minR;
        for (int i = r - 1; i >= minR; i--) {
            if (board[i][c] != 0) {
                goal = i + 1;
                break;
            }
        }
        if (goal != r) {
            board[goal][c] = board[r][c];
            board[r][c] = 0;
        }
        if (goal > minR && board[goal][c] == board[goal - 1][c]) {
            board[goal - 1][c] = 2 * board[goal - 1][c];
            board[goal][c] = 0;
            return goal;
        }

        return minR;

    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {

        int minR = 0;
        for (int i = 0; i < board.length; i++) {
            minR = moveTileUpAsFarAsPossible(board, i, c, minR);
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {


        for (int c = 0; c < board[0].length; c++) {
            tiltColumn(board, c);
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        if (side == Side.NORTH) {
            tiltUp(board);
            return;
        } else if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            return;
        } else if (side == Side.SOUTH) {
            rotateRight(board);
            rotateRight(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
            return;
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            return;
        } else {
            System.out.println("Invalid side specified");
        }
    }
}
