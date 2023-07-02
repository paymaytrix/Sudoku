package com.sudoku.controller;


import com.sudoku.model.Move;
import com.sudoku.service.SudokuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/sudoku")
@RequiredArgsConstructor
public class SudokuController {

    private SudokuService sudokuService;

    @GetMapping ("/startSudokuGame")
    public ResponseEntity<String> startSudokuGame()
    {
        sudokuService = new SudokuService();
        return sudokuService.startGame();
    }

    @GetMapping("/showSudokuGame")
    public String showSudokuGame()
    {
        return sudokuService.showSudoku();
    }

    @PostMapping("/moveSudokuGame")
    public ResponseEntity<String> makeGameMove(@RequestBody Move moveRequest) {
        if (sudokuService == null) {
            return ResponseEntity.badRequest().body("Game not started");
        }
        return sudokuService.makeSudokuMove(moveRequest);
    }

}
