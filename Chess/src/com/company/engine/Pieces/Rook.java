package com.company.engine.Pieces;

import com.company.engine.Alliance;
import com.company.engine.Board.Board;
import com.company.engine.Board.BoardUtils;
import com.company.engine.Board.Move;
import com.company.engine.Board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.company.engine.Board.Move.*;

public class Rook extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8,-1,1,8};

    public Rook(final int position,final Alliance alliance) {
        super(PieceType.ROOK,position, alliance,true);
    }
    public Rook(final int position,final Alliance alliance,boolean isFirstMove) {
        super(PieceType.ROOK,position, alliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int candidateCoordinatesOffsets : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.position;
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExclusion(candidateDestinationCoordinate,candidateCoordinatesOffsets) ||
                   isEighthColumnExclusion(candidateDestinationCoordinate,candidateCoordinatesOffsets)) {
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinatesOffsets;
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                    }
                    else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();
                        if(this.alliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getDestinationCoordinate(),move.getMovedPiece().getAlliance());
    }
}
