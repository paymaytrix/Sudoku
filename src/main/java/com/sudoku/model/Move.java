package com.sudoku.model;

import lombok.Data;

@Data
public class Move {
    private int row;
    private int col;
    private int value;

    public Move(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }
}
