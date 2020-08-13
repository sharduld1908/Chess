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

import static com.company.Board.Move.*;

public class Rook extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8,-1,1,8};

    public Rook(int position, Alliance alliance) {
        super(position, alliance);
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
}
