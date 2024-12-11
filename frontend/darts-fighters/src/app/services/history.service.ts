import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private listUrl = '/api/history/list';

  constructor(private http: HttpClient) { }

  getList(): Observable<any> {
    return this.http.get(this.listUrl);
  }
}
