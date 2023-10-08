// noinspection JSUnusedGlobalSymbols

import type { Meta, StoryObj } from '@storybook/vue3';
import StartController from "@/features/Start/views/StartController.vue";

const meta = {
  title: 'Start/Home',
  component: StartController,
  tags: [ 'autodocs' ],
  render: (args) => ({
    components: { StartController },
    setup: () => ({ args }),
    template: `
      <start-controller/>
    `,
    methods: {}
  })
} satisfies Meta<typeof StartController>;

export default meta;
type Story = StoryObj<typeof meta>;

export const StartPage: Story = {
  args: {
  },
};
