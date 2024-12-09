import { Row } from "./row.model";

export class Game {
  constructor(
    public id:number = 0,
    public rows: Row[] = [],
    public players: any[] = [],
  ) {}
}