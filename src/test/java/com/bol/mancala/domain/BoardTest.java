package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.WrongPlayerException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void givenAPlayerWhenItIsNotTheRightPlayerForCurrentTurnThenThrowWrongPlayerException() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST, board.turn());

        Throwable exception = assertThrows(WrongPlayerException.class, () -> board.play(3, Board.Player.SECOND));

        assertEquals("Not the turn of SECOND player", exception.getMessage());
    }

    @Test
    void givenABoardWhenPlayEndsThenTurnIsChangedAndStonesAreMoved() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST, board.turn());

        board.play(2, Board.Player.FIRST);

        BoardSide top = board.top;
        assertEquals(List.of(6, 6, 0, 7, 7, 7), top.pits());
        assertEquals(1, top.big());

        BoardSide bottom = board.bottom;
        assertEquals(List.of(7, 7, 6, 6, 6, 6), bottom.pits());
        assertEquals(0, bottom.big());

        assertEquals(Board.Player.SECOND, board.turn());
    }

    @Test
    void givenTheBoardThenSimulatePlaysInTheBoard() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST, board.turn());

        board.play(2, Board.Player.FIRST);
        assertSideState(List.of(6, 6, 0, 7, 7, 7), 1, board.top);
        assertSideState(List.of(7, 7, 6, 6, 6, 6), 0, board.bottom);
        assertEquals(Board.Player.SECOND, board.turn());

        board.play(4, Board.Player.SECOND);
        assertSideState(List.of(7, 7, 1, 8, 7, 7), 1, board.top);
        assertSideState(List.of(7, 7, 6, 6, 0, 7), 1, board.bottom);
        assertEquals(Board.Player.FIRST, board.turn());

        board.play(0, Board.Player.FIRST);
        assertSideState(List.of(0, 8, 2, 9, 8, 8), 2, board.top);
        assertSideState(List.of(8, 7, 6, 6, 0, 7), 1, board.bottom);
        assertEquals(Board.Player.SECOND, board.turn());

        board.play(3, Board.Player.SECOND);
        assertSideState(List.of(1, 9, 3, 9, 8, 8), 2, board.top);
        assertSideState(List.of(8, 7, 6, 0, 1, 8), 2, board.bottom);
        assertEquals(Board.Player.FIRST, board.turn());

        board.play(5, Board.Player.FIRST);
        assertSideState(List.of(2, 9, 3, 9, 8, 0), 3, board.top);
        assertSideState(List.of(9, 8, 7, 1, 2, 9), 2, board.bottom);
        assertEquals(Board.Player.SECOND, board.turn());

        board.play(5, Board.Player.SECOND);
        assertSideState(List.of(3, 10, 4, 10, 9, 1), 3, board.top);
        assertSideState(List.of(10, 9, 7, 1, 2, 0), 3, board.bottom);
        assertEquals(Board.Player.FIRST, board.turn());
    }

    @Test
    void whenLastDropHappensOnCurrentPlayerLargerPitThenPlayerHasAnotherTurn() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        assertEquals(Board.Player.FIRST, board.turn());
        assertEquals(0, board.top.amountOfStonesInLargerPit());

        board.play(0, Board.Player.FIRST);

        assertEquals(1, board.top.amountOfStonesInLargerPit());
        assertEquals(Board.Player.FIRST, board.turn());
    }

    @Test
    void whenLastDroppedPitContainsOnlyOneStoneButOpponentHasNoStonesThenDoNotCollectOpponentStones() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        board.top.pits().set(0, 1);
        board.top.pits().set(1, 0);
        board.bottom.pits().set(4, 0);

        board.play(0, Board.Player.FIRST);

        BoardSide firstPlayer = board.top;
        assertEquals(1, firstPlayer.amountOfStonesInPit(1));
        assertEquals(0, firstPlayer.amountOfStonesInLargerPit());

        BoardSide secondPlayerSide = board.bottom;
        assertEquals(0, secondPlayerSide.amountOfStonesInPit(4));
        assertEquals(0, secondPlayerSide.amountOfStonesInLargerPit());
    }

    @Test
    void whenLastDroppedPitContainsOnlyOneStoneAndOpponentStonesThenCollectOpponentStones() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        board.top.pits().set(0, 1);
        board.top.pits().set(1, 0);

        board.play(0, Board.Player.FIRST);

        BoardSide firstPlayer = board.top;
        assertEquals(0, firstPlayer.amountOfStonesInPit(1));
        assertEquals(7, firstPlayer.amountOfStonesInLargerPit());

        BoardSide secondPlayerSide = board.bottom;
        assertEquals(0, secondPlayerSide.amountOfStonesInPit(4));
        assertEquals(0, secondPlayerSide.amountOfStonesInLargerPit());
    }

    @Test
    void whenOneSideRunsOutOfStonesThenFinishTheGame() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        List<Integer> pits = board.top.pits();
        pits.set(0, 0);
        pits.set(1, 0);
        pits.set(2, 0);
        pits.set(3, 0);
        pits.set(4, 0);
        pits.set(5, 1);

        board.play(5, Board.Player.FIRST);

        assertEquals(Board.Player.SECOND, board.winner());
        assertTrue(board.isFinished());
    }

    @Test
    void whenGameIsFinishedAndPlayersHaveDifferentAmountOfStonesInTheirBigPitThenBoardOneWinner() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");
        board.play(0, Board.Player.FIRST);
        board.play(1, Board.Player.FIRST);

        board.top.finish();
        board.bottom.finish();

        assertEquals(Board.Player.SECOND, board.winner());
        assertTrue(board.isFinished());
    }

    @Test
    void whenGameIsFinishedAndBothPlayerHaveTheSameAmountOfStonesInTheirBigPitThenBoardHasNoWinner() {
        Board board = Board.create("Arthur Dent", "Ford Prefect");

        board.top.finish();
        board.bottom.finish();

        assertNull(board.winner());
        assertTrue(board.isFinished());
    }

    private void assertSideState(List<Integer> expectedPits, int expectedLargerPit, BoardSide side) {
        assertEquals(expectedPits, side.pits());
        assertEquals(expectedLargerPit, side.big());
    }
}
