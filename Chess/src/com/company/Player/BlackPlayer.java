package com.company.Player;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.Move;
import com.company.Pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,final Collection<Move> blackStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
