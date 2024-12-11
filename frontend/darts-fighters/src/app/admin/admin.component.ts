import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from './model/user.model';
import { response } from 'express';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  users: User[] = [];

  constructor (private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getList().subscribe({
      next: (response) => {
        this.users = response;
      }
    });
  }

  onDelete(userId: number) {
    this.userService.deleteUser(userId).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }

  onPromote(userId: number) {
    this.userService.promoteUser(userId).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }
}
