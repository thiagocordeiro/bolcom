import type { Board, Game, MancalaClient, Play } from "@/features/Mancala/Mancala";
import axios, { type AxiosResponse } from "axios";

const http = axios.create({
    baseURL: "",
    timeout: 10000,
    withCredentials: true,
    validateStatus: () => true,
  },
)

export default {
  async history(): Promise<Array<Game>> {
    const response: AxiosResponse<Array<Game>> = await http.get(`/api/games/mancala`);

    if (response.status != 200) {
      return [];
    }

    return response.data
  },
  async start(firstPlayer: string, secondPlayer: string): Promise<string> {
    const response: AxiosResponse<{ id: string }> = await http.post("/api/games/mancala", {
      firstPlayer: firstPlayer,
      secondPlayer: secondPlayer
    });

    return response.data.id
  },
  async load(id: string): Promise<Board> {
    const response: AxiosResponse<Board> = await http.get(`/api/games/mancala/${id}`);

    return response.data
  },
  async play(play: Play): Promise<void> {
    await http.post(`/api/games/mancala/${play.game}`, {
      player: play.turn,
      pit: play.pit,
    });
  },
} satisfies MancalaClient
