import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GameService } from '../services/game.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Game, nullPlayer, Player } from './model/game.model';
import { firstValueFrom } from 'rxjs';
import { Row } from './model/row.model';
import { ThrowModel } from './model/throw.model';

@Component({
  selector: 'app-game',
  imports: [FormsModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {
  console = console;
  activePlayer: number = 0;
  currentRound: number = 1;
  currentDart: number = 1;
  currentThrowId: number = 0;
  multiplier: number = 1;
  gameId: string = '';
  game: Game = new Game();
  @Input() currentThrow: number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private gameService: GameService) {
    this.gameId = this.route.snapshot.paramMap.get('id') ?? '';
  }

  async ngOnInit() {
    this.getState();
  }

  async getState() {
    const response = await firstValueFrom(this.gameService.getGame(this.gameId));
    this.game.id = response.game.id;
    this.game.players = Object.entries(response.game.players).map(elem => ({id: parseInt(elem[0]), username: elem[1]} as Player) )
    this.game.rows = response.game.rows;
    this.game.winner = response.game.winner;
    let rows: Row[] = [];
    let dartNum = 1;
    // sort as the turns go
    this.game.rows.forEach((row, rindex) => {
      let rowModel: Row = new Row(row.id, []);
      this.game.players.forEach((player) => {
        this.game.rows[rindex].throwsList.forEach((uthrow) => {
          if (player.id == uthrow.userId && uthrow.dartNumber == dartNum) {
            let throwModel = new ThrowModel(uthrow.throwId, uthrow.dartNumber, uthrow.score, uthrow.userId);
            rowModel.throwsList.push(throwModel);
            dartNum++;
          }
        })
        dartNum = 1;
      })
      rows.push(rowModel);
    })
    this.game.rows = rows;
    this.refreshCurrents();
  }

  toggleMultiplier(multiplier: number) {
    this.multiplier = this.multiplier === multiplier ? 1 : multiplier;
  }

  win(player: number) {
    const playerObject: Player = this.game.players.find((elem) => elem.id === player) ?? nullPlayer;
    this.gameService.winGame(this.gameId, playerObject.id).subscribe({
      next: () => {
        this.alertAndRedirect(playerObject);
      }
    });
  }

  alertAndRedirect(playerObject: Player) {
    alert(playerObject.username + " won the game!");
    this.router.navigate(['/menu']);
  }

  addScore() {
    const newRow: number = this.game.players.slice(-1)[0].id == this.activePlayer && this.currentDart === 3 ? 1 : 0;
    this.gameService.userThrow(this.gameId, this.currentThrowId, this.currentThrow * this.multiplier, newRow).subscribe({
      next: () => {
        this.console.log(this.activePlayer, this.currentRound, this.sumScores(this.activePlayer, this.currentRound));
        if (this.sumScores(this.activePlayer, this.currentRound) - this.currentThrow <= 0) {
          this.win(this.activePlayer);
          return;
        }
        this.getState();
      }
    });
  }

  refreshCurrents() {
    let found = false;
    for (let i=0; i < this.game.rows.length; i++) {
      for (let j=0; j < this.game.rows[i].throwsList.length; j++) {
        const uthrow = this.game.rows[i].throwsList[j];
        if (uthrow.score == -1 && !found) {
          found = true;
          this.currentDart = uthrow.dartNumber;
          this.activePlayer = uthrow.userId;
          this.currentThrowId = uthrow.throwId;
        }
      }
      this.currentRound = i;
    }
    if (this.game.winner) {
      const playerObject: Player = this.game.players.find((elem) => elem.id === this.activePlayer) ?? nullPlayer;
      this.alertAndRedirect(playerObject);
    }
  }

  sumScores(player: number, round: number): number {
    let sum = 501;
    for (let i=0; i <= round; i++) {
      this.game.rows[i].throwsList?.forEach((value) => {
        if (value.userId == player) {
          sum -= value.score && value.score > 0 ? value.score : 0;
        }
      });
    }

    return sum;
  }
}
