import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private optionsUrl = '/api/user/options'
  private listUrl = '/api/user/list';
  private deleteUrl = '/api/user/{userId}'
  private promoteUrl = '/api/user/{userId}/promote'

  constructor(private http: HttpClient) { }

  getOptions(): Observable<any> {
    const headers = new HttpHeaders();
    return this.http.get(this.optionsUrl, { headers });
  }

  getList(): Observable<any> {
    return this.http.get(this.listUrl + "?sort=username%2Casc");
  }

  deleteUser(userId: number): Observable<any> {
    return this.http.delete(this.deleteUrl.replace('{userId}', '' + userId));
  }

  promoteUser(userId: number): Observable<any> {
    return this.http.post(this.promoteUrl.replace('{userId}', '' + userId), {});
  }
}
