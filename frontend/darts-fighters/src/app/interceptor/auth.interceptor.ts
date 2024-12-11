import { HttpRequest, HttpHandlerFn } from '@angular/common/http';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
    const authToken = localStorage.getItem('token');

    if (authToken) {
        const newReq = req.clone({
            headers: req.headers.append('Authorization', 'Bearer ' + authToken),
          });
        return next(newReq);
    }

    return next(req);

  }