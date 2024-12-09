import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { GameService } from '../services/game.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-menu',
  imports: [],
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.css'
})
export class MainMenuComponent {
  protected userRole: string = localStorage.getItem('role') ?? '';
  constructor(
    private authService: AuthService,
    private gameService: GameService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.authService.getMe().subscribe({
      next: (response) => {
        localStorage.setItem('username', response.username);
        localStorage.setItem('role', response.role);
        localStorage.setItem('userId', response.userId);
        this.userRole = response.role;
      },
      error: () => {
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        localStorage.removeItem('userId');
        this.router.navigate(['/login']);
      }
    })
  }

  onLogout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  onNewGame() {
    this.gameService.createNewGame().subscribe({
      next: (response) => {
        this.router.navigate(['/lobby/' + response.game]);
      }
    })

  }
}
