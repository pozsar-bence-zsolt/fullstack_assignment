import { Row } from "./row.model";

export type Player = {
  id: number;
  username: string;
}

export const nullPlayer = {id: 0, username: "unknown"} as Player;

export class Game {
  constructor(
    public id:number = 0,
    public rows: Row[] = [],
    public players: Player[] = [],
    public winner?: number,
  ) {}
}