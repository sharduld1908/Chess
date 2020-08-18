package com.company.gui;

import com.company.engine.Board.Board;
import com.company.engine.Board.BoardUtils;
import com.company.engine.Board.Move;
import com.company.engine.Board.Tile;
import com.company.engine.Pieces.Piece;
import com.company.engine.Player.MoveTransition;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece playerMovedPiece;

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private BoardDirection boardDirection;

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    private Board chessBoard;
    private boolean highlightLegalMoves;

    public Table() {
        this.chessBoard = Board.createStandardBoard();

        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.boardPanel= new BoardPanel();
        this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(createFileMenu());
        jMenuBar.add(createPreferencesMenu());
        return jMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(e -> System.out.println("opens up that pgn file"));
        fileMenu.add(openPGN);
        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        fileMenu.add(exit);
        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferences = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferences.add(flipBoardMenuItem);

        preferences.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlighterCheck  = new JCheckBoxMenuItem("Highlight legal moves",false);
        legalMoveHighlighterCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheck.isSelected();
            }
        });
        preferences.add(legalMoveHighlighterCheck);
        return preferences;
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0;i< BoardUtils.NUM_TILES;i++) {
                final TilePanel tilePanel = new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(Board chessBoard) {
             removeAll();
             for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                 tilePanel.drawTile(chessBoard);
                 add(tilePanel);
             }
             validate();
             repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        public TilePanel(final BoardPanel boardPanel,
                         final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        playerMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        if(sourceTile == null) {                    //first click
                            sourceTile = chessBoard.getTile(tileId);
                            playerMovedPiece = sourceTile.getPiece();
                            if(playerMovedPiece == null) {
                                sourceTile = null;
                            }
                        }else {                                     //second click
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard,sourceTile.getTileNumber(),destinationTile.getTileNumber());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                //TODO add the move to the logs
                            }
                            sourceTile = null;
                            destinationTile = null;
                            playerMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        public void  drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()) {
                String pieceIconPath = "D:\\Chess\\Chess\\Chess\\assets\\Chess Pieces\\";
                try {
                    final BufferedImage image = ImageIO.read(
                            new File(pieceIconPath + board.getTile(this.tileId).getPiece().getAlliance().toString()
                                    + board.getTile(this.tileId).getPiece().toString()
                                    + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                }catch (IOException e) {
                    System.out.println(pieceIconPath + board.getTile(this.tileId).getPiece().getAlliance().toString()
                            + board.getTile(this.tileId).getPiece().toString()
                            + ".png");
                    e.printStackTrace();
                }
            }
        }

        private void highlightLegals(final Board board) {
            if(highlightLegalMoves) {
                for(final Move move : pieceLegalMove(board)) {
                    if(move.getDestinationCoordinate() == tileId) {
                        try{
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\Chess\\Chess\\Chess\\assets\\images\\green_dot.png")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMove(Board board) {
            if(playerMovedPiece != null && playerMovedPiece.getAlliance() == board.currentPlayer().getAlliance()) {
                return playerMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }


        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileId] ||
                    BoardUtils.SIXTH_RANK[this.tileId] ||
                    BoardUtils.FOURTH_RANK[this.tileId] ||
                    BoardUtils.SECOND_RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SEVENTH_RANK[this.tileId] ||
                    BoardUtils.FIFTH_RANK[this.tileId] ||
                    BoardUtils.THIRD_RANK[this.tileId]  ||
                    BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}