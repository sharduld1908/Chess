package com.company.Pieces;

import com.company.Alliance;
import com.company.Board.Board;
import com.company.Board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int position;
    protected final Alliance alliance;
    protected final boolean isFirstMove;
    private final int cacheHashCode;

    Piece(final PieceType pieceType,final int position,final Alliance alliance) {
        this.position = position;
        this.alliance = alliance;
        this.isFirstMove = false;
        this.pieceType = pieceType;
        this.cacheHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + alliance.hashCode();
        result = 31 * result * position;
        result = 31 * result * (isFirstMove ? 1:0);
        return result;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }
    public int getPosition() {
        return this.position;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public abstract Piece movePiece(Move move);

    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) obj;
        return alliance == otherPiece.getAlliance() && position == otherPiece.getPosition()
                && isFirstMove == otherPiece.isFirstMove() && pieceType == otherPiece.getPieceType();
    }

    @Override
    public int hashCode() {
        return this.cacheHashCode;
    }

    public enum PieceType {
        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();
    }
}
