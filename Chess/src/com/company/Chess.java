package com.company;

import com.company.Board.Board;

public class Chess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
