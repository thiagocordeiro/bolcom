<template>
  <div class="container mt-5">
    <board-view v-if="board != null" :board="board" @play="play"/>
    <div class="text-center" v-else>Starting...</div>
  </div>

</template>

<script lang="ts">
import { defineComponent } from "vue"
import BoardView from "@/features/Mancala/view/BoardView.vue";
import type { Board, MancalaClient, Play } from "@/features/Mancala/Mancala";
import client from "@/infrastructure/client";

type Data = {
  board: Board | null,
}

export default defineComponent({
  components: { BoardView },
  setup: () => ({
    client: client as MancalaClient,
  }),
  data: (): Data => ({
    board: null,
  }),
  mounted() {
    this.load();
  },
  methods: {
    async load() {
      const id = this.$route.params.id as string
      this.board = await client.load(id) as Board;
    },
    async play(play: Play) {
      await client.play(play)
      await this.load()
    }
  }
})
</script>
