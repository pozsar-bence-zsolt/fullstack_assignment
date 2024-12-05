import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Registration } from '../register/model/registration.model';
import { Login } from '../login/model/login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'api/login';
  private registerUrl = 'api/register';

  constructor(private http: HttpClient) { }

  sendRegister(registration: Registration): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.registerUrl, registration, { headers });
  }

  sendLogin(login: Login): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.loginUrl, login, { headers });
  }
}
