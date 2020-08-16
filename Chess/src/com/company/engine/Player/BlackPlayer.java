package com.company.engine.Player;

import com.company.engine.Alliance;
import com.company.engine.Board.Board;
import com.company.engine.Board.Move;
import com.company.engine.Board.Tile;
import com.company.engine.Pieces.Piece;
import com.company.engine.Pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.company.engine.Board.Move.*;

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

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(board,playerKing,6,(Rook) rookTile.getPiece(),rookTile.getTileNumber(),5));
                    }
                }
            }
            if (!this.board.getTile(1).isTileOccupied() && this.board.getTile(2).isTileOccupied() && this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(board,playerKing,2,(Rook) rookTile.getPiece(),rookTile.getTileNumber(),3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
