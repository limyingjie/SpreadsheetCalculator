package com.company;

import java.util.Stack;

public class Spreadsheet {
    private Cell[][] cells;
    private int rows;
    private int columns;

    Spreadsheet(int rows, int columns){
        this.cells = new Cell[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    public void setCell(int row, int column, String content){
        cells[row][column]= new Cell(row,column,content);
    }

    public void evaluateSpreadsheet(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                Cell cell = cells[i][j];
                evaluateCell(cell);
                if (i+1==rows && j+1==columns) System.out.printf("%.5f", cell.evaluatedValue);
                else System.out.printf("%.5f%n", cell.evaluatedValue);
            }
        }
    }

    private double evaluateCell(Cell cell){
        if (cell.nowEvaluating) throw new RuntimeException("Cyclic dependency detected at cell (" +
                cell.row+","+cell.column+") with content ("+cell.content+"). Now exiting.");
        cell.nowEvaluating = true;
        if (!cell.isEvaluated) {
            String[] terms = cell.content.split(" ");
            Stack<Double> stack = new Stack<>();

            for (int i = 0; i < terms.length; i++) {
                if (Character.isAlphabetic(terms[i].charAt(0))){
                    int row = Utils.letterToNumber(terms[i].charAt(0))-1;
                    int column = Integer.parseInt(terms[i].substring(1))-1;
                    stack.push(evaluateCell(this.cells[row][column]));
                }
                else if (terms[i].equals("+")){
                    stack.push(stack.pop()+stack.pop());
                }
                else if (terms[i].equals("-")){
                    stack.push(-(stack.pop()-stack.pop()));
                }
                else if (terms[i].equals("*")){
                    stack.push(stack.pop()*stack.pop());
                }
                else if (terms[i].equals("/")){
                    double denominator = stack.pop();
                    stack.push(stack.pop()/denominator);
                }
                else if (terms[i].equals("++")){
                    stack.push(stack.pop()+1);
                }
                else if (terms[i].equals("--")){
                    stack.push(stack.pop()-1);
                }
                else{
                    stack.push(Double.parseDouble(terms[i]));
                }
            }
            cell.evaluatedValue = stack.pop();
            cell.isEvaluated = true;
        }
        cell.nowEvaluating = false;
        return cell.evaluatedValue;
    }

    public static void main(String[] args) {
        Spreadsheet spreadsheet = Loader.load();
        spreadsheet.evaluateSpreadsheet();
    }
}
