import { Component, Input } from '@angular/core';
import { Login } from './model/login.model';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @Input() login: Login;

  constructor(private authService: AuthService) {
    this.login = new Login();
  }

  onSubmit() {
    this.authService.sendLogin(this.login).subscribe({
      next: (response) => {
        localStorage.removeItem('token');
        localStorage.setItem('token', response["jwt-token"])
        window.location.href = '/menu';
      },
      error: (error) => {
        console.error('Error occurred:', error);
      }
    });
  }
}
