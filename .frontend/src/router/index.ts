import { createRouter, createWebHistory } from 'vue-router'
import StartController from "@/features/Start/views/StartController.vue";
import MancalaController from "@/features/Mancala/view/MancalaController.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'start', component: StartController },
    { path: '/games/mancala/:id', name: 'game', component: MancalaController }

    ///games/mancala/xxx
  ]
})

export default router
