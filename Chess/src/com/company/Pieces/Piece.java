package com.company.Pieces;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int position;
    protected final Alliance alliance;

    Piece(final int position,final Alliance alliance) {
        this.position = position;
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
