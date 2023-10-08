package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.WrongPlayerException;

public class Board {
    public final BoardSide top;
    public final BoardSide bottom;
    private Player turn;

    public enum Player {FIRST, SECOND}

    private record Drop(BoardSide side, int pit) {
    }

    private Board(BoardSide top, BoardSide bottom) {
        this.top = top;
        this.bottom = bottom;
        this.turn = Player.FIRST;
    }

    public static Board create(String firstPlayer, String secondPlayer) {
        return new Board(
                BoardSide.setup(firstPlayer),
                BoardSide.setup(secondPlayer)
        );
    }

    public Player turn() {
        return this.turn;
    }

    public void play(int pitIndex, Player playing) {
        if (playing != turn) throw new WrongPlayerException(playing);

        BoardSide playerSide = playerSide();
        BoardSide opponentSide = opponentSide();
        int stones = playerSide.grabAllStonesFromPit(pitIndex);

        // make sure we start dropping stones in the next pit
        int nextPit = pitIndex + 1;
        Drop lastDrop = distribute(nextPit, stones, playerSide, opponentSide);

        tryToCloseTheGame();

        if (lastDrop.pit == BoardSide.LARGER_PIT_INDEX) {
            return;
        }

        if (lastDrop.side == playerSide) {
            tryToCollectOpponentStones(lastDrop);
        }

        turn = (turn == Player.FIRST) ? Player.SECOND : Player.FIRST;
    }

    private Drop distribute(int pitIndex, int numberOfStones, BoardSide playerSide, BoardSide opponentSide) {
        boolean isPlaySide = playerSide == playerSide();
        int pitNumberLimit = Math.min((pitIndex + numberOfStones), BoardSide.NUMBER_OF_PITS);
        int pit;

        for (pit = pitIndex; pit < pitNumberLimit; pit++) {
            playerSide.dropStoneInPit(pit);
            numberOfStones--;
        }

        if (isPlaySide && numberOfStones > 0) {
            playerSide.dropStoneInLargerPit(1);
            numberOfStones--;

            if (numberOfStones == 0) {
                return new Drop(playerSide, BoardSide.LARGER_PIT_INDEX);
            }
        }

        if (numberOfStones == 0) {
            // since for loop increased one for number to create the skip condition
            // we have to decrease the number to make sure the last used pit is returned

            return new Drop(playerSide, pit - 1);
        }

        return distribute(0, numberOfStones, opponentSide, playerSide);
    }

    private void tryToCollectOpponentStones(Drop drop) {
        int playerStones = drop.side.amountOfStonesInPit(drop.pit);
        int opponentPit = BoardSide.NUMBER_OF_PITS - drop.pit - 1;
        int opponentStones = opponentSide().amountOfStonesInPit(opponentPit);

        if (playerStones != 1) {
            return;
        }

        if (opponentStones == 0) {
            // TODO test
            return;
        }

        int grabbedPlayerStone = playerSide().grabAllStonesFromPit(drop.pit);
        playerSide().dropStoneInLargerPit(grabbedPlayerStone);

        int grabbedOpponentStones = opponentSide().grabAllStonesFromPit(opponentPit);
        playerSide().dropStoneInLargerPit(grabbedOpponentStones);
    }

    private void tryToCloseTheGame() {
        int smallerAmountOfStones = Math.min(top.playableStones(), bottom.playableStones());

        if (smallerAmountOfStones > 0) {
            return;
        }

        top.finish();
        bottom.finish();
    }

    public BoardSide playerSide() {
        return turn == Player.FIRST ? this.top : this.bottom;
    }

    public BoardSide opponentSide() {
        return turn == Player.FIRST ? this.bottom : this.top;
    }

    public boolean isFinished() {
        return Math.max(top.playableStones(), bottom.playableStones()) == 0;
    }

    public BoardSide winner() {
        if (!isFinished()) {
            return null;
        }

        if (top.amountOfStonesInLargerPit() == bottom.amountOfStonesInLargerPit()) {
            // it is a tie
            return null;
        }

        return top.amountOfStonesInLargerPit() > bottom.amountOfStonesInLargerPit() ? top : bottom;
    }
}
