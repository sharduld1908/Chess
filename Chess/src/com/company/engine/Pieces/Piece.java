package com.company.engine.Pieces;

import com.company.engine.Alliance;
import com.company.engine.Board.Board;
import com.company.engine.Board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int position;
    protected final Alliance alliance;
    protected final boolean isFirstMove;
    private final int cacheHashCode;

    Piece(final PieceType pieceType,final int position,final Alliance alliance,final boolean isFirstMove) {
        this.position = position;
        this.alliance = alliance;
        this.isFirstMove = isFirstMove;
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

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

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
                && isFirstMove == otherPiece.isFirstMove && pieceType == otherPiece.getPieceType();
    }

    @Override
    public int hashCode() {
        return this.cacheHashCode;
    }

    public enum PieceType {
        PAWN("Pawn",100) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("Knight",300) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("Bishop",30) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("Rook",500) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Queen",900) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("King",10000) {
            @Override
            public boolean isKing() {
                return true;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        };

        private final String pieceName;
        private final int pieceValue;
        PieceType(final String pieceName,final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();

        public int getPieceValue() {
            return this.pieceValue;
        }
    }
}
