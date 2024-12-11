import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './app/interceptor/auth.interceptor';
import { routes } from './app/app.routes';
import { provideRouter } from '@angular/router';

bootstrapApplication(AppComponent, 
  {
    providers: [
      provideHttpClient(withInterceptors([authInterceptor])),
      provideRouter(routes),
    ]
  }
).catch((err) => console.error(err));
