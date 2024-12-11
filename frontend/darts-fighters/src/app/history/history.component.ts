import { Component } from '@angular/core';
import { HistoryService } from '../services/history.service';
import { Game, nullPlayer, Player } from '../game/model/game.model';
import { Row } from '../game/model/row.model';
import { ThrowModel } from '../game/model/throw.model';

@Component({
  selector: 'app-history',
  imports: [],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
  games: Game[] = [];
  console = console;

  constructor (private historyService: HistoryService) {}

  ngOnInit() {
    this.historyService.getList().subscribe({
      next: (response) => {
        this.games = response;
        this.games.forEach((game, index) => {
          const players = Object.entries(game.players as any).map(elem => ({id: parseInt(elem[0]), username: elem[1]} as Player));
          this.games[index].players = players;

          let rows: Row[] = [];
          // sort as the turns go
          this.games[index].rows.forEach((row) => {
            let rowModel: Row = new Row(row.id, []);
            
            players.forEach((player) => {
              const playerThrows = row.throwsList.filter(uthrow => uthrow.userId === player.id);
              
              playerThrows.sort((a, b) => a.dartNumber - b.dartNumber).forEach((uthrow) => {
                const throwModel = new ThrowModel(uthrow.throwId, uthrow.dartNumber, uthrow.score, uthrow.userId);
                rowModel.throwsList.push(throwModel);
              });
            });
            rows.push(rowModel);
          });
          
          this.games[index].rows = rows;
        });
        console.log(this.games);
      }
    });
  }

  sumScores(gameIndex: number, player: number, round: number): number {
    let sum = 501;
    for (let i=0; i <= round; i++) {
      this.games[gameIndex].rows[i].throwsList?.forEach((value) => {
        if (value.userId == player) {
          sum -= value.score && value.score > 0 ? value.score : 0;
        }
      });
    }

    return sum;
  }

  getPlayer(gameNumber: number, playerId: number): string {
    const founds: Player | undefined = this.games[gameNumber].players.find((value) => value.id == playerId);
    return founds?.username ?? '';
  }
}
