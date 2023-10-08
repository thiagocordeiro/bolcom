<template>
  <div class="container" style="min-width: 992px">
    <h1>
      {{ board.top.player }}
      <span v-if="isFirstPlayerTurn">👈</span>
      <span v-if="isFirstPlayerWinner">🏆</span>
    </h1>
    <div class="row pt-4" :class="board.finished ? `finished`: ``">
      <div class="col-2">
        <pit-view class="text-bg-primary"
                  style="height: 204px"
                  turn="FIRST"
                  :active="false"
                  :stones="board.top.big"
                  :pit="1000"
        />
      </div>

      <div class="col-8">
        <div class="row">
          <div class="col" :key="index" v-for="(stones, index) in firstPlayerPits">
            <pit-view class="text-bg-primary"
                      turn="FIRST"
                      :active="isFirstPlayerTurn"
                      :stones="stones"
                      @select="onSelect"
                      :pit="firstPlayerPits.length - index - 1"/>
          </div>
        </div>

        <div class="row pt-4">
          <div class="col" :key="index" v-for="(stones, index) in secondPlayerPits">
            <pit-view class="text-bg-secondary"
                      turn="SECOND"
                      :active="isSecondPlayerTurn"
                      :stones="stones"
                      @select="onSelect"
                      :pit="index"/>
          </div>
        </div>
      </div>

      <div class="col-2">
        <pit-view class="text-bg-secondary"
                  style="height: 204px"
                  turn="SECOND"
                  :active="false"
                  :stones="board.bottom.big"
                  :pit="1000"/>
      </div>
    </div>
    <br>
    <h1 class="text-end">
      <span v-if="isSecondPlayerWinner">🏆</span>
      <span v-if="isSecondPlayerTurn">👉</span>
      {{ board.bottom.player }}
    </h1>

    <hr class="mt-5 mb-5"/>

    <button class="btn btn-primary" @click="$router.push(`/`)">Go To Start</button>

    <div class="pt-5">
      <a href="https://www.linkedin.com/in/thiagocordeirooo/" target="_blank">https://www.linkedin.com/in/thiagocordeirooo</a>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, type PropType } from "vue"
import PitView from "./PitView.vue";
import type { Board, Play } from "@/features/Mancala/Mancala";

export default defineComponent({
  components: { PitView },
  props: {
    board: {
      type: Object as PropType<Board>,
      required: true,
    },
  },
  data: () => ({}),
  computed: {
    isTiedMatch() {
      return this.board.finished && this.board.winner == null
    },
    isFirstPlayerWinner() {
      return this.board.finished && (this.isTiedMatch || this.board.winner == "FIRST")
    },
    isSecondPlayerWinner() {
      return this.board.finished && (this.isTiedMatch || this.board.winner == "SECOND")
    },
    isFirstPlayerTurn() {
      return this.board.turn == "FIRST" && !this.board.finished
    },
    isSecondPlayerTurn() {
      return this.board.turn == "SECOND" && !this.board.finished
    },
    firstPlayerPits() {
      return [ ...this.board.top.pits ].reverse();
    },
    secondPlayerPits() {
      return this.board.bottom.pits;
    }
  },
  methods: {
    onSelect(selected: Play) {
      this.$emit("play", { ...selected, game: this.board.id } satisfies Play)
    }
  }
})
</script>

<style>
.finished {
  opacity: 0.3;
}
</style>
