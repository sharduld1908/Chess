package com.company.engine.Pieces;

import com.company.engine.Alliance;
import com.company.engine.Board.Board;
import com.company.engine.Board.BoardUtils;
import com.company.engine.Board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.company.engine.Board.Move.*;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final int position,final Alliance alliance) {
        super(PieceType.PAWN,position, alliance,true);
    }
    public Pawn(final int position,final Alliance alliance,boolean isFirstMove) {
        super(PieceType.PAWN,position, alliance,isFirstMove);
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
                legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
            } else if (currentCoordinatesOffsets == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[this.position] && this.alliance.isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.position] && this.alliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.position + (this.getAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCoordinatesOffsets == 7 &&
                    !(BoardUtils.EIGHTH_COLUMN[this.position] && this.alliance.isWhite() ||
                            BoardUtils.FIRST_COLUMN[this.position] && this.alliance.isBlack())) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.alliance != pieceOnCandidate.getAlliance()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate,pieceOnCandidate));
                    }
                }
            } else if (currentCoordinatesOffsets == 9 &&
                    !(BoardUtils.EIGHTH_COLUMN[this.position] && this.alliance.isBlack() ||
                            BoardUtils.FIRST_COLUMN[this.position] && this.alliance.isWhite())) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.alliance != pieceOnCandidate.getAlliance()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate,pieceOnCandidate));
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

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getAlliance());
    }
}
