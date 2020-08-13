package com.company.Pieces;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.BoardUtils;
import com.company.Board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final int position, final Alliance alliance) {
        super(position, alliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCoordinatesOffsets : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.position + (this.alliance.getDirection() * currentCoordinatesOffsets);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCoordinatesOffsets == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCoordinatesOffsets == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.position] && this.alliance.isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.position] && this.alliance.isWhite())) {
                final int behindCandidateDestinationCoordinate = this.position + (this.getAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCoordinatesOffsets == 7 &&
                    !(BoardUtils.EIGHTH_COLUMN[this.position] && this.alliance.isWhite() ||
                            BoardUtils.FIRST_COLUMN[this.position] && this.alliance.isBlack())) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.alliance != pieceOnCandidate.getAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (currentCoordinatesOffsets == 9 &&
                    !(BoardUtils.EIGHTH_COLUMN[this.position] && this.alliance.isBlack() ||
                            BoardUtils.FIRST_COLUMN[this.position] && this.alliance.isWhite())) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.alliance != pieceOnCandidate.getAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
