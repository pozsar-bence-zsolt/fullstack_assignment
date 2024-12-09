import { Component, Input } from '@angular/core';
import { Option } from '../types/common';
import { UserService } from '../services/user.service';
import { GameService } from '../services/game.service';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-lobby',
  imports: [FormsModule],
  templateUrl: './lobby.component.html',
  styleUrl: './lobby.component.css'
})
export class LobbyComponent {
  @Input() players: string[];
  private gameId: string = '';
  protected username: string = localStorage.getItem('username') ?? '';
  protected userId: string = localStorage.getItem('userId') ?? '';
  protected userOptions: Option<String>[] = [];

  constructor (
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private gameService: GameService
  ) {
    this.gameId = this.route.snapshot.paramMap.get('id') ?? '';
    this.players = [this.userId, "empty", "empty", "empty"];
  }

  ngOnInit() {
    this.userService.getOptions().subscribe({
      next: (response) => {
        this.userOptions = response;
      } 
    })
  }

  ready() {
    this.gameService.ready(this.gameId, this.players).subscribe({
      next: () => {
        this.router.navigate(['/game/' + this.gameId]);
      }
    });
  }

}
