package com.bol.mancala.domain;

import com.bol.mancala.domain.exception.EmptyPitException;
import com.bol.mancala.domain.exception.InvalidPitException;

import java.util.ArrayList;
import java.util.List;

public class BoardSide {
    public static final int NUMBER_OF_PITS = 6;
    public static final int LARGER_PIT_INDEX = 1000;

    private final String player;
    private final List<Integer> pits;
    private int big;

    private BoardSide(String player, List<Integer> pits, int big) {
        this.player = player;
        this.pits = pits;
        this.big = big;
    }

    static BoardSide setup(String player) {
        List<Integer> initialPits = new ArrayList<>(List.of(6, 6, 6, 6, 6, 6));

        return new BoardSide(player, initialPits, 0);
    }

    public String player() {
        return player;
    }

    public List<Integer> pits() {
        return pits;
    }

    public int big() {
        return big;
    }

    public int amountOfStonesInPit(int pitIndex) {
        try {
            return pits.get(pitIndex);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidPitException(pitIndex);
        }
    }

    public int amountOfStonesInLargerPit() {
        return big;
    }

    public int grabAllStonesFromPit(int pitIndex) {
        try {
            int stones = pits.get(pitIndex);
            if (stones == 0) throw new EmptyPitException(pitIndex);

            pits.set(pitIndex, 0);

            return stones;
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidPitException(pitIndex);
        }
    }

    public void dropStoneInPit(int pitIndex) {
        int pit = pits.get(pitIndex);
        pits.set(pitIndex, pit + 1);
    }

    public void dropStoneInLargerPit(int amountOfStones) {
        big = big + amountOfStones;
    }

    public int playableStones() {
        int total = 0;

        for (int stones : pits) {
            total += stones;
        }

        return total;
    }

    public void finish() {
        int total = playableStones();
        dropStoneInLargerPit(total);

        for (int pit = 0; pit < NUMBER_OF_PITS; pit++) {
            pits.set(pit, 0);
        }
    }
}
