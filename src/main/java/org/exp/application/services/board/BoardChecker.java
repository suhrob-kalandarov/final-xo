package org.exp.application.services.board;

import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class BoardChecker {
    public boolean checkWin(int[][] board, int player) {
        for (int i = 0; i < 3; i++)
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;

        for (int j = 0; j < 3; j++)
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player)
                return true;

        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public boolean checkDraw(int[][] board) {
        for (int[] row : board)
            for (int cell : row)
                if (cell == 0)
                    return false;
        return true;
    }

    public boolean isBoardFull(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == Constants.EMPTY) return false;
            }
        }
        return true;
    }
}
