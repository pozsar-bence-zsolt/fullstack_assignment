import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { LobbyComponent } from './lobby/lobby.component';
import { GameComponent } from './game/game.component';
import { AdminComponent } from './admin/admin.component';
import { HistoryComponent } from './history/history.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'menu', component: MainMenuComponent },
    { path: 'lobby/:id', component: LobbyComponent },
    { path: 'game/:id', component: GameComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'history', component: HistoryComponent },
    { path: '**', component: PageNotFoundComponent },
];
