package com.company.Pieces;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int position;
    protected final Alliance alliance;
    protected final boolean isFirstMove;

    Piece(final int position,final Alliance alliance) {
        this.position = position;
        this.alliance = alliance;
        this.isFirstMove = false;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }
    public int getPosition() {
        return this.position;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");


        private String pieceName;
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
