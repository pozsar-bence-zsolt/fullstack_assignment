import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GameService } from '../services/game.service';
import { ActivatedRoute } from '@angular/router';
import { Game } from './model/game.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-game',
  imports: [FormsModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {
  console = console;
  activePlayer: number = 0;
  currentRound: number = 0;
  currentDart: number = 0;
  multiplier: number = 1;
  gameId: string = '';
  game: Game = new Game();
  @Input() currentThrow: number = 0;

  constructor(private route: ActivatedRoute, private gameService: GameService) {
    this.gameId = this.route.snapshot.paramMap.get('id') ?? '';
  }

  async ngOnInit() {
    const response = await firstValueFrom(this.gameService.getGame(this.gameId));
    this.game.id = response.game.id;
    this.game.rows = response.game.rows;
    this.game.players = Object.entries(response.game.players).map(elem => ({id: elem[0], username: elem[1]}) )
    this.refreshCurrents();
  }

  toggleMultiplier(multiplier: number) {
    this.multiplier = this.multiplier === multiplier ? 1 : multiplier;
  }

  addScore() {
    
  }

  nextTurn() {
    // if (this.activePlayer < this.players.length - 1) {
    //   this.activePlayer++;
    // } else {
    //   this.activePlayer = 0;
    //   this.currentRound++;
    //   if (!this.rounds.includes(this.currentRound)) {
    //     this.rounds.push(this.currentRound);
    //   }
    // }
  }

  refreshCurrents() {
    let found = false;
    for (let i=0; i < this.game.rows.length; i++) {
      for (let j=0; j < this.game.rows[i].throwsList.length; j++) {
        const uthrow = this.game.rows[i].throwsList[j];
        if (uthrow.score == -1 && !found) {
          found = true;
          this.currentDart = uthrow.dartNumber;
          this.currentRound = j;
          this.activePlayer = uthrow.userId;
        }
      }
    }
  }

  sumScores(player: number, round: number): number {
    let sum = 0;
    this.game.rows[round].throwsList?.forEach((value) => {
      if (value.userId == player) {
        sum += value.score && value.score > 0 ? value.score : 0;
      }
    });
    return sum;
  }
}
