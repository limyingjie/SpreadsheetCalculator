package com.company;

import java.util.Scanner;

public class Loader {
    public static Spreadsheet load(){
        Scanner sc = new Scanner(System.in);
        String[] line;
        int rows;
        int columns;

        if (sc.hasNextLine()) {
            line = sc.nextLine().split(" ");
            if (line.length == 2){
                try{
                    columns = Integer.parseInt(line[0]);
                    rows = Integer.parseInt(line[1]);
                }
                catch (NumberFormatException e){
                    throw new NumberFormatException("Width/height input is not an integer");
                }
            }
            else throw new IllegalArgumentException("Invalid input");
        }
        else throw new IllegalArgumentException("No input found");

        Spreadsheet spreadsheet = new Spreadsheet(rows,columns);
        //System.out.println("Loading a "+rows+" by "+columns+" spreadsheet.");
        System.out.println(columns+" "+rows);

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (sc.hasNextLine()) {
                    spreadsheet.setCell(i,j,sc.nextLine());
                }
                else throw new IllegalArgumentException("No input found");
            }
        }
        //System.out.println("Load complete.");
        return spreadsheet;
    }
}
