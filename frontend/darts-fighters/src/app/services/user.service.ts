import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private optionsUrl = '/api/user/options'

  constructor(private http: HttpClient) { }

  getOptions(): Observable<any> {
    const headers = new HttpHeaders();
    return this.http.get(this.optionsUrl, { headers });
  }
}
