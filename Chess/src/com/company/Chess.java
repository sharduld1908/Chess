package com.company;

import com.company.engine.Board.Board;
import com.company.gui.Table;

public class Chess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);

        Table table = new Table();
    }
}
