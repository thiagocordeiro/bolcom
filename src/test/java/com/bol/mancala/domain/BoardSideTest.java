package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.EmptyPitException;
import com.bol.mancala.domain.exception.InvalidPitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardSideTest {
    @Test
    void givenTheNameThenSetupBoardSide() {
        String name = "Arthur Dent";

        BoardSide side = BoardSide.setup(name);

        assertEquals("Arthur Dent", side.player());
        assertEquals(List.of(6, 6, 6, 6, 6, 6), side.pits());
        assertEquals(0, side.big());
    }

    @Test
    void givenTheIndexOfANonExistingPitWhenItEmptyThenThrowInvalidPitException() {
        BoardSide side = BoardSide.setup("Arthur Dent");

        Throwable exception = Assertions.assertThrows(InvalidPitException.class, () -> side.grabAllStonesFromPit(10));

        assertEquals("Pit 10 does not exist", exception.getMessage());
    }

    @Test
    void givenTheIndexOfAnExistingPitThenGrabAllContainingStones() {
        BoardSide side = BoardSide.setup("Arthur Dent");

        int stones = side.grabAllStonesFromPit(3);

        assertEquals(6, stones);
    }

    @Test
    void givenTheIndexOfAnExistingPitWhenPitIsEmptyThenThrowEmptyPitException() {
        BoardSide side = BoardSide.setup("Arthur Dent");
        side.grabAllStonesFromPit(3);

        Throwable exception = Assertions.assertThrows(EmptyPitException.class, () -> side.grabAllStonesFromPit(3));

        assertEquals("Pit 3 is empty", exception.getMessage());
    }

    @Test
    void givenTheIndexOfAnExistingPitThenReturnTheAmountOfStonesInIt() {
        BoardSide side = BoardSide.setup("Arthur Dent");

        int stones = side.amountOfStonesInPit(3);

        assertEquals(6, stones);
    }

    @Test
    void givenTheIndexOfAnExistingPitWhenADropIsMadeThenSumOneStoneIntoTheAmount() {
        BoardSide side = BoardSide.setup("Arthur Dent");
        int pitIndex = 3;
        assertEquals(6, side.amountOfStonesInPit(pitIndex));

        side.dropStoneInPit(pitIndex);

        assertEquals(7, side.amountOfStonesInPit(pitIndex));
    }

    @Test
    void givenTheBoardSideWhenADropIsMadeIntoTheLargerPitThenSumOneStoneIntoTheAmount() {
        BoardSide side = BoardSide.setup("Arthur Dent");
        assertEquals(0, side.amountOfStonesInLargerPit());

        side.dropStoneInLargerPit(3);

        assertEquals(3, side.amountOfStonesInLargerPit());
    }

    @Test
    void whenFinishIsInvokedThenCollectAllStonesToBigPit() {
        BoardSide side = BoardSide.setup("Arthur Dent");
        assertEquals(36, side.playableStones());
        assertEquals(0, side.big());

        side.finish();

        assertEquals(0, side.playableStones());
        assertEquals(36, side.big());
    }
}
