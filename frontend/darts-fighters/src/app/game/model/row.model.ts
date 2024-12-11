import { ThrowModel } from "./throw.model";

export class Row {
    constructor(
      public id:number,
      public throwsList:ThrowModel[],
    ) {}
  }
  