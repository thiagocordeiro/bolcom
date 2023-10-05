package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.WrongPlayerException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    @Test
    void givenAPlayerWhenItIsNotTheRightPlayerForCurrentTurnThenThrowWrongPlayerException() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());

        Throwable exception = assertThrows(WrongPlayerException.class, () -> board.play(3, Board.Player.SECOND_PLAYER));

        assertEquals("Not the turn of SECOND_PLAYER", exception.getMessage());
    }

    @Test
    void givenABoardWhenPlayEndsThenTurnIsChangedAndStonesAreMoved() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());

        board.play(2, Board.Player.FIRST_PLAYER);

        BoardSide top = board.top;
        assertEquals(List.of(6, 6, 0, 7, 7, 7), top.pits());
        assertEquals(1, top.big());

        BoardSide bottom = board.bottom;
        assertEquals(List.of(7, 7, 6, 6, 6, 6), bottom.pits());
        assertEquals(0, bottom.big());

        assertEquals(Board.Player.SECOND_PLAYER, board.turn());
    }

    @Test
    void givenTheBoardThenSimulatePlaysInTheBoard() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());

        board.play(2, Board.Player.FIRST_PLAYER);
        assertSideState(List.of(6, 6, 0, 7, 7, 7), 1, board.top);
        assertSideState(List.of(7, 7, 6, 6, 6, 6), 0, board.bottom);
        assertEquals(Board.Player.SECOND_PLAYER, board.turn());

        board.play(4, Board.Player.SECOND_PLAYER);
        assertSideState(List.of(7, 7, 1, 8, 7, 7), 1, board.top);
        assertSideState(List.of(7, 7, 6, 6, 0, 7), 1, board.bottom);
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());

        board.play(0, Board.Player.FIRST_PLAYER);
        assertSideState(List.of(0, 8, 2, 9, 8, 8), 2, board.top);
        assertSideState(List.of(8, 7, 6, 6, 0, 7), 1, board.bottom);
        assertEquals(Board.Player.SECOND_PLAYER, board.turn());

        board.play(3, Board.Player.SECOND_PLAYER);
        assertSideState(List.of(1, 9, 3, 9, 8, 8), 2, board.top);
        assertSideState(List.of(8, 7, 6, 0, 1, 8), 2, board.bottom);
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());

        board.play(5, Board.Player.FIRST_PLAYER);
        assertSideState(List.of(2, 9, 3, 9, 8, 0), 3, board.top);
        assertSideState(List.of(9, 8, 7, 1, 2, 9), 2, board.bottom);
        assertEquals(Board.Player.SECOND_PLAYER, board.turn());

        board.play(5, Board.Player.SECOND_PLAYER);
        assertSideState(List.of(3, 10, 4, 10, 9, 1), 3, board.top);
        assertSideState(List.of(10, 9, 7, 1, 2, 0), 3, board.bottom);
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());
    }

    @Test
    void whenLastDropHappensOnCurrentPlayerLargerPitThenPlayerHasAnotherTurn() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());
        // first round
        board.play(5, Board.Player.FIRST_PLAYER);
        board.play(0, Board.Player.SECOND_PLAYER);
        // second round
        board.play(4, Board.Player.FIRST_PLAYER);
        board.play(2, Board.Player.SECOND_PLAYER);

        // third round
        board.play(5, Board.Player.FIRST_PLAYER);

        assertEquals(Board.Player.FIRST_PLAYER, board.turn());
    }

    @Test
    void whenLastDropHappensOnCurrentPlayerSideAndLastUsedPitContainsOnlyOneStoneThenCollectOpponentStones() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST_PLAYER, board.turn());
        // first round
        board.play(5, Board.Player.FIRST_PLAYER);
        board.play(0, Board.Player.SECOND_PLAYER);
        // second round
        board.play(4, Board.Player.FIRST_PLAYER);
        board.play(2, Board.Player.SECOND_PLAYER);
        // third round
        board.play(5, Board.Player.FIRST_PLAYER);
        // forth round
        board.play(4, Board.Player.FIRST_PLAYER);
        assertEquals(Board.Player.SECOND_PLAYER, board.turn());

        BoardSide secondPlayerSide = board.playerSide();
        assertEquals("Ford Prefect", secondPlayerSide.player());
        assertEquals(0, secondPlayerSide.amountOfStonesInPit(0));
        assertEquals(2, secondPlayerSide.amountOfStonesInLargerPit());

        BoardSide firstPlayer = board.opponentSide();
        assertEquals("Arthur Dent", firstPlayer.player());
        assertEquals(0, firstPlayer.amountOfStonesInPit(5));
        assertEquals(5, firstPlayer.amountOfStonesInLargerPit());
    }

    @Test
    void whenOneSideRunsOutOfStonesThenFinishTheGame() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        board.play(0, Board.Player.FIRST_PLAYER);
        board.play(1, Board.Player.FIRST_PLAYER);
        board.play(0, Board.Player.SECOND_PLAYER);
        board.play(0, Board.Player.FIRST_PLAYER);
        board.play(5, Board.Player.SECOND_PLAYER);
        board.play(2, Board.Player.FIRST_PLAYER);
        board.play(4, Board.Player.SECOND_PLAYER);
        board.play(1, Board.Player.FIRST_PLAYER);
        board.play(0, Board.Player.SECOND_PLAYER);
        board.play(3, Board.Player.FIRST_PLAYER);
        board.play(2, Board.Player.SECOND_PLAYER);
        board.play(0, Board.Player.FIRST_PLAYER);
        board.play(1, Board.Player.SECOND_PLAYER);
        board.play(3, Board.Player.FIRST_PLAYER);
        board.play(0, Board.Player.SECOND_PLAYER);
        board.play(0, Board.Player.FIRST_PLAYER);
        board.play(3, Board.Player.SECOND_PLAYER);
        board.play(2, Board.Player.SECOND_PLAYER);
        board.play(5, Board.Player.FIRST_PLAYER);
        board.play(4, Board.Player.SECOND_PLAYER);
        board.play(0, Board.Player.FIRST_PLAYER);
        board.play(3, Board.Player.SECOND_PLAYER);
        board.play(3, Board.Player.FIRST_PLAYER);
        board.play(2, Board.Player.SECOND_PLAYER);
        board.play(4, Board.Player.FIRST_PLAYER);
        board.play(5, Board.Player.FIRST_PLAYER);

        assertEquals(board.bottom, board.winner);
    }

    private void assertSideState(List<Integer> expectedPits, int expectedLargerPit, BoardSide side) {
        assertEquals(expectedPits, side.pits());
        assertEquals(expectedLargerPit, side.big());
    }
}
