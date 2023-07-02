package com.sudoku.service;

import com.sudoku.model.Move;
import com.sudoku.model.SudokuGame;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class    SudokuService {
    private SudokuGame sudokuGame;

    public ResponseEntity<String> startGame() {
        sudokuGame = new SudokuGame();
        sudokuGame.initialize();
        return ResponseEntity.ok("READY");
    }


    public ResponseEntity<String> makeSudokuMove(Move move) {
        boolean isValid = sudokuGame.makeMove(move.getRow(), move.getCol(), move.getValue());
        if (isValid) {
            if (sudokuGame.isSolved()) {
                return ResponseEntity.ok("Sudoku solved");
            }
            return ResponseEntity.ok("Valid");
        }

        if (sudokuGame.shouldSuggestMove()) {
            Move suggestedMove = sudokuGame.getSuggestedMove();
            return ResponseEntity.badRequest().body("Invalid. Try suggested move: row " + suggestedMove.getRow() +
                    ", col " + suggestedMove.getCol() +
                    ", value " + suggestedMove.getValue());
        }

        return ResponseEntity.badRequest().body("Invalid move");
    }


    public String showSudoku() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sudoku Board:\n");

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sb.append(sudokuGame.getBoard()[row][col]);
                if (col < 8) {
                    sb.append(" ");
                    if (col % 3 == 2) {
                        sb.append("| ");
                    }
                }
            }
            sb.append("\n");
            if (row < 8) {
                if (row % 3 == 2) {
                    sb.append("---------------------\n");
                }
            }
        }

        return sb.toString();
    }



}
