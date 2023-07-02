package com.sudoku.model;

import java.util.Random;


public class SudokuGame {
    private static final int BOARD_SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private static final int NUM_EMPTY_CELLS = 40; // Number of cells to leave empty
    private int cntInvalidMoves;
    private Move suggestedMove;
    private int[][] board;


    public void initialize() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        //initializeEmptyBoard();
        solveSudoku();
        removeCells(NUM_EMPTY_CELLS);
    }

    public int[][] getBoard()
    {
        return board;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) && !usedInColumn(board, col, num)
                && !usedInBox(board, row - row % 3 , col - col % 3, num);
    }

    private boolean solveSudoku() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku()) {
                                return true;
                            }
                            board[row][col] = EMPTY_CELL; // Backtrack if solution not found
                        }
                    }
                    return false; // No valid number found, backtrack to previous cell
                }
            }
        }
        return true; // Sudoku solved
    }


    private boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }


    private void removeCells(int numCellsToRemove) {
        Random random = new Random();

        while (numCellsToRemove > 0) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                numCellsToRemove--;
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }


/*
*  Making moves
* */


    public boolean makeMove(int row, int col, int value) {
        if (!isValidMove(row, col, value)) {
            cntInvalidMoves++;
            if (cntInvalidMoves >= 3) {
                suggestedMove = getSuggestedMove();
            }
            return false;
        }

        board[row][col] = value;
        cntInvalidMoves = 0;
        suggestedMove = null;
        return true;
    }

    private boolean isValidMove(int row, int col, int value) {
        return isValidRow(row, value) &&
                isValidColumn(col, value) &&
                isValidBlock(row, col, value);
    }

    private boolean isValidRow(int row, int value) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidColumn(int col, int value) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidBlock(int row, int col, int value) {
        int blockRowStart = row - row % 3;
        int blockColStart = col - col % 3;

        for (int i = blockRowStart; i < blockRowStart + 3; i++) {
            for (int j = blockColStart; j < blockColStart + 3; j++) {
                if (board[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean shouldSuggestMove() {
        return suggestedMove != null;
    }

    public Move getSuggestedMove() {
        if (suggestedMove == null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0) {
                        for (int value = 1; value <= 9; value++) {
                            if (isValidMove(row, col, value)) {
                                suggestedMove = new Move(row, col, value);
                                return suggestedMove;
                            }
                        }
                    }
                }
            }
        }
        return suggestedMove;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0 || !isValidMove(i, j, board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }


}
