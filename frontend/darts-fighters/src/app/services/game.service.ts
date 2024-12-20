import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private newGameUrl = 'api/game/new-game';
  private readyUrl = 'api/game/{gameId}/ready';
  private getGameUrl = 'api/game/{gameId}';
  private userThrowUrl = 'api/game/{gameId}/throw';
  private winGameUrl = 'api/game/{gameId}/win';

  constructor(private http: HttpClient) { }

  createNewGame(): Observable<any> {
    const headers = new HttpHeaders();
    return this.http.get(this.newGameUrl, { headers });
  }

  ready(gameId: string, players: string[]): Observable<any> {
    const headers = new HttpHeaders();
    return this.http.post(this.readyUrl.replace("{gameId}", gameId), players , { headers });
  }

  getGame(gameId: string): Observable<any> {
    return this.http.get(this.getGameUrl.replace("{gameId}", gameId));
  }

  userThrow(gameId: string, throwId: number, score: number, newRow: number): Observable<any> {
    return this.http.post(this.userThrowUrl.replace("{gameId}", gameId), {"throwId" : throwId, "score": score, "newRow": newRow});
  }

  winGame(gameId: string, player: number) {
    return this.http.post(this.winGameUrl.replace("{gameId}", gameId), {"winner": player});
  }
}
