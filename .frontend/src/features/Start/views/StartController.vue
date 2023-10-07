<template>
  <div class="container pt-5" style="max-width: 600px">
    <form @submit.prevent="startGame">
      <div class="form-group">
        <label for="first">First Player</label>
        <input v-model="first" class="form-control" id="first" required minlength="3">
      </div>
      <div class="form-group mt-3">
        <label for="second">Second Player</label>
        <input v-model="second" class="form-control" id="second" required minlength="3">
      </div>

      <div class="mt-3">
        <button class="btn btn-primary" type="submit">Start New Game</button>
      </div>
    </form>

    <div v-if="history.length > 0">
      <hr class="mt-5 mb-4"/>
      <h2>Game History</h2>

      <ul class="list-group">
        <li class="list-group-item"
            v-for="game in games"
            :key="game.id"
            @click="$router.push(`/games/mancala/${game.id}`)">
          {{ game.first }} vs {{ game.second }}
        </li>
      </ul>
    </div>

  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
import type { Game, MancalaClient } from "@/features/Mancala/Mancala";
import client from "@/infrastructure/client";

export default defineComponent({
  setup: () => ({
    client: client as MancalaClient,
  }),
  data: () => ({
    first: "",
    second: "",
    history: [],
  }),
  computed: {
    games(): Array<Game> {
      return this.history;
    }
  },
  mounted() {
    this.loadHistory()
  },
  methods: {
    async loadHistory() {
      this.history = await this.client.history()
    },
    async startGame() {
      const gameId = await this.client.start(this.first, this.second)

      this.$router.push(`/games/mancala/${gameId}`)
    }
  }
})
</script>

<style scoped>
.list-group {
  cursor: pointer;
}
</style>
