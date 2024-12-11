import { Component, Input } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { FormsModule } from "@angular/forms";
import { Registration } from './model/registration.model';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  @Input() registration: Registration;

  constructor(private authService: AuthService) {
    this.registration = new Registration();
  }

  onSubmit() {
    this.authService.sendRegister(this.registration).subscribe({
      next: () => {
        window.location.href = '/login';
      },
      error: (error) => {
        console.error('Error occurred:', error);
      }
    });
  }
}
