// noinspection JSUnusedGlobalSymbols

import type { Meta, StoryObj } from '@storybook/vue3';
import PitView from "@/features/Mancala/view/PitView.vue";
import alertObject from "@/features/__stories__/alertObject";
import type { Play } from "@/features/Mancala/Mancala";

const meta = {
  title: 'Mancala/Pit',
  component: PitView,
  tags: [ 'autodocs' ],
  render: (args) => ({
    components: { PitView },
    setup: () => ({ args }),
    template: `
      <pit-view :pit="args.pit"
                :turn="args.turn"
                :stones="args.stones"
                :active="args.active"
                @select="onSelect"/>
    `,
    methods: {
      onSelect(selected: Play) {
        alertObject(selected)
      },
    }
  })
} satisfies Meta<typeof PitView>;

export default meta;
type Story = StoryObj<typeof meta>;

export const InactivePit: Story = {
  args: {
    pit: 1,
    turn: "FIRST",
    stones: 6,
    active: false,
  },
};

export const ActivePit: Story = {
  args: {
    pit: 1,
    turn: "FIRST",
    stones: 6,
    active: true,
  },
};
