export type Game = {
  id: string
  first: string
  second: string
}

export type Board = {
  id: string
  top: BoardSide
  bottom: BoardSide
  turn: Turn
  winner?: Turn
}

export type BoardSide = {
  player: Turn
  pits: Array<number>
  big: number
}

export type Turn = "FIRST" | "SECOND"

export type Play = {
  pit: number
  turn: Turn | string
  game?: string
}

export type MancalaClient = {
  history(): Promise<Array<Game>>
  start(firstPlayer: string, secondPlayer: string): Promise<string>
  load(id: string): Promise<Board>
  play(play: Play): Promise<void>
}
