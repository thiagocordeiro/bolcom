// noinspection JSUnusedGlobalSymbols

import type { Meta, StoryObj } from '@storybook/vue3';
import BoardView from "@/features/Mancala/view/BoardView.vue";
import type { Board, Play } from "@/features/Mancala/Mancala";
import alertObject from "@/features/__stories__/alertObject";

const meta = {
  title: 'Mancala/Board',
  component: BoardView,
  tags: [ 'autodocs' ],
  render: (args) => ({
    components: { BoardView },
    setup: () => ({ args }),
    template: `
      <board-view :board="args.board" @play="onPlay"/>
    `,
    methods: {
      onPlay(selected: Play) {
        alertObject(selected)
      },
    }
  })
} satisfies Meta<typeof BoardView>;

export default meta;

export const FirstPlayer: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        player: "Arthur",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      bottom: {
        player: "Dent",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      turn: "FIRST",
      finished: false,
    } satisfies Board
  },
};

export const SecondPlayer: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        player: "Arthur",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      bottom: {
        player: "Dent",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      turn: "SECOND",
      finished: false,
    } satisfies Board
  },
};

export const FinishedTiedMatch: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        player: "Arthur",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 50
      },
      bottom: {
        player: "Dent",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 50
      },
      turn: "SECOND",
      finished: true,
    } satisfies Board
  },
};

export const FirstPlayerWinner: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        player: "Arthur",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 100
      },
      bottom: {
        player: "Dent",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 0
      },
      turn: "SECOND",
      finished: true,
      winner: "FIRST"
    } satisfies Board
  },
};

export const SecondPlayerWinner: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        player: "Arthur",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 0
      },
      bottom: {
        player: "Dent",
        pits: [ 0, 0, 0, 0, 0, 0 ],
        big: 100
      },
      turn: "SECOND",
      finished: true,
      winner: "SECOND"
    } satisfies Board
  },
};
