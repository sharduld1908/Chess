package com.company.engine.Pieces;

import com.company.engine.Alliance;
import com.company.engine.Board.Board;
import com.company.engine.Board.BoardUtils;
import com.company.engine.Board.Move;
import com.company.engine.Board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.company.engine.Board.Move.*;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-6,-10,-15,-17,6,10,15,17};

    public Knight(final int position,final Alliance alliance) {
        super(PieceType.KNIGHT,position, alliance,true);
    }
    public Knight(final int position,final Alliance alliance,boolean isFirstMove) {
        super(PieceType.KNIGHT,position, alliance,isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            if(isFirstColumnExclusion(this.position,currentCandidateOffset) ||
               isSecondColumnExclusion(this.position,currentCandidateOffset) ||
               isSeventhColumnExclusion(this.position,currentCandidateOffset) ||
               isEighthColumnExclusion(this.position,currentCandidateOffset)) {
                continue;
            }

            final int candidateDestinationCoordinate = this.position + currentCandidateOffset;
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
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10  || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || candidateOffset == -6);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10  || candidateOffset == -6 || candidateOffset == -15);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getDestinationCoordinate(),move.getMovedPiece().getAlliance());
    }
}
