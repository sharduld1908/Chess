package com.company.Board;

import com.company.Pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.*;

import static com.company.Board.BoardUtils.NUM_TILES;

public abstract class Tile {
    protected final int tileNumber;
    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    private Tile(final int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public static Tile createTile(final int tileNumber, final Piece piece) {
        return piece != null ? new OccupiedTile(tileNumber, piece) : EMPTY_TILES.get(tileNumber);
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        private EmptyTile(final int number) {
            super(number);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {
        private final Piece piece;

        private OccupiedTile(final int number, Piece piece) {
            super(number);
            this.piece = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}
