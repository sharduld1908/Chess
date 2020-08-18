package com.company.engine.Player;

import com.company.engine.Board.Board;
import com.company.engine.Board.Move;

public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
        return transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return moveStatus;
    }
}
