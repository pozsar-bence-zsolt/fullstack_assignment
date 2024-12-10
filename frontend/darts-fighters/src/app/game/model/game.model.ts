import { Row } from "./row.model";

export type Player = {
  id: number;
  username: string;
}

export class Game {
  constructor(
    public id:number = 0,
    public rows: Row[] = [],
    public players: Player[] = [],
  ) {}
}