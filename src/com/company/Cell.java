package com.company;

public class Cell {
    int row;
    int column;
    String content;
    boolean isEvaluated = false;
    boolean nowEvaluating = false;
    double evaluatedValue;

    Cell(int row, int column, String content){
        this.row =  row;
        this.column = column;
        this.content = content;
    }
}
