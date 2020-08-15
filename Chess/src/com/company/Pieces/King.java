package com.company.Pieces;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.BoardUtils;
import com.company.Board.Move;
import com.company.Board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-1,-7,-8,-9,1,7,8,9};
    public King(final int position,final Alliance alliance) {
        super(PieceType.KING,position, alliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCoordinatesOffsets : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.position + currentCoordinatesOffsets;
            if (isFirstColumnExclusion(this.position,currentCoordinatesOffsets) ||
                isEighthColumnExclusion(this.position,currentCoordinatesOffsets)) {
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate));
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();
                    if(this.alliance != pieceAtDestinationAlliance) {
                        legalMoves.add(new Move.AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9  || candidateOffset == 7);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset == 9  || candidateOffset == -7);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(),move.getMovedPiece().getAlliance());
    }


}
