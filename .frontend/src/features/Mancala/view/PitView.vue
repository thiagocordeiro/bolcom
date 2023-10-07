<template>
  <div :class="`card pit-card ${ active ? 'active':'' } pit-${pit}`"
       @click="onClick"
       style="height: 90px">
    <div class="card-body text-center">
      <h1>{{ stones }}</h1>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
import type { Play } from "@/features/Mancala/Mancala";

export default defineComponent({
  props: {
    active: {
      type: Boolean,
      required: true,
    },
    turn: {
      type: String,
      required: true,
    },
    pit: {
      type: Number,
      required: true,
    },
    stones: {
      type: Number,
      required: true,
    },
  },
  methods: {
    onClick() {
      if (!this.active) {
        return;
      }

      this.$emit("select", { pit: this.pit, turn: this.turn } satisfies Play)
    },
  }
})
</script>


<style scoped>
.pit-card.active {
  cursor: pointer;
}
</style>
