package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.WrongPlayerException;

public class Board {
    public final BoardSide top;
    public final BoardSide bottom;
    private Player turn;
    public BoardSide winner;

    public enum Player {FIRST_PLAYER, SECOND_PLAYER}

    private record Drop(BoardSide side, int pit) {
    }

    private Board(BoardSide top, BoardSide bottom) {
        this.top = top;
        this.bottom = bottom;
        this.turn = Player.FIRST_PLAYER;
    }

    static Board create(String firstPlayer, String secondPlayer) {
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

        if (lastDrop.pit == BoardSide.LARGER_PIT_INDEX) {
            return;
        }

        if (lastDrop.side == playerSide) {
            tryToCollectOpponentStones(lastDrop);
        }

        tryToCloseTheGame();

        turn = (turn == Player.FIRST_PLAYER) ? Player.SECOND_PLAYER : Player.FIRST_PLAYER;
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

        if (playerStones != 1) {
            return;
        }

        int grabbedPlayerStone = playerSide().grabAllStonesFromPit(drop.pit);
        playerSide().dropStoneInLargerPit(grabbedPlayerStone);

        int opponentPit = BoardSide.NUMBER_OF_PITS - drop.pit - 1;
        int grabbedOpponentStones = opponentSide().grabAllStonesFromPit(opponentPit);
        playerSide().dropStoneInLargerPit(grabbedOpponentStones);
    }

    private void tryToCloseTheGame() {
        int smallerAmountOfStones = Math.min(top.playableStones(), bottom.playableStones());
        System.out.println(smallerAmountOfStones);

        if (smallerAmountOfStones > 0) {
            return;
        }

        top.finish();
        bottom.finish();

        winner = top.amountOfStonesInLargerPit() > bottom.amountOfStonesInLargerPit() ? top : bottom;
    }

    public BoardSide playerSide() {
        return turn == Player.FIRST_PLAYER ? this.top : this.bottom;
    }

    public BoardSide opponentSide() {
        return turn == Player.FIRST_PLAYER ? this.bottom : this.top;
    }

    public BoardSide winner() {
        return winner;
    }
}
