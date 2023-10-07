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
        turn: "FIRST",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      bottom: {
        turn: "SECOND",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      turn: "FIRST",
    } satisfies Board
  },
};

export const SecondPlayer: StoryObj<typeof meta> = {
  args: {
    board: {
      id: "game-xxx",
      top: {
        turn: "FIRST",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      bottom: {
        turn: "SECOND",
        pits: [ 1, 2, 3, 4, 5, 6 ],
        big: 0
      },
      turn: "SECOND",
    } satisfies Board
  },
};
