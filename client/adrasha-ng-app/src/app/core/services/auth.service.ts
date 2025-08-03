import { inject, Injectable } from '@angular/core';
import { AuthenticationService } from '@core/api/authentication/authentication.service';
import {
  LoginRequest,
  RegistrationRequest,
  UserDTO,
  UserDTORolesItem,
} from '@core/model/authService';
import { Token } from '@core/model/Token';
import { TokenService } from '@core/services';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  of,
  share,
  switchMap,
  tap,
  throwError
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly authenticationService = inject(AuthenticationService);
  private readonly tokenService = inject(TokenService);

  private currentUser$ = new BehaviorSubject<UserDTO | null>(null);
  private initialized$ = new BehaviorSubject<boolean>(false);

  construcor() {
    this.init();
  }

  init() {
    if (this.tokenService.valid()) {
      return this.loadCurrentUser();
    }
    this.initialized$.next(true);
    return of(null);
  }

  readonly isLoggedIn$ = this.currentUser$.pipe(
    // tap((value) => console.log('user:', value)),
    map((user) => !!user)
  );

  check(): Observable<boolean> {
    return this.initialized$.pipe(
      // tap((value) => console.log('init status : ' + value)),
      switchMap(() => this.isLoggedIn$),
      // tap((value) => console.log('check status : ' + value)),
      map((loggedIn) => loggedIn && this.tokenService.valid())
    );
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.authenticationService.loginUser(loginRequest).pipe(
      tap((response) => {
        const token: Token = {
          accessToken: response.accessToken,
          exp: response.exp,
          expiresIn: response.expiresIn,
          tokenType: response.tokenType,
        };
        this.tokenService.set(token);
      }),
      switchMap(() => this.loadCurrentUser()),
      switchMap(() => this.check()),
      catchError((error) => {
        console.error('AuthService error:', error);
        return throwError(() => error);
      })
    );
  }

  register(registerRequest: RegistrationRequest): Observable<UserDTO> {
    return this.authenticationService.registerUser(registerRequest).pipe(
      catchError((error) => {
        console.error('AuthService error:', error);
        return throwError(() => error);
      })
    );
  }

  private loadCurrentUser(): Observable<UserDTO | null> {
    return this.authenticationService.getCurrentUser().pipe(
      tap((user) => {
        this.currentUser$.next(user || null);
      }),
      catchError((error) => {
        console.error('Failed to load current user:', error);
        this.currentUser$.next(null);
        return of(null);
      })
    );
  }

  logout(): void {
    this.tokenService.clear();
    this.currentUser$.next(null);
    this.initialized$.next(true); // Reset initialization state
  }

  get currentUser(): Observable<UserDTO | null> {
    return this.currentUser$.pipe(share());
  }

  // Expose initialized$ for guards to wait on
  waitForInitialization(): Observable<boolean> {
    return this.initialized$.asObservable();
  }

  isAdmin(): Observable<boolean> {
    return this.currentUser.pipe(
      map((user) => user?.roles?.includes(UserDTORolesItem.ADMIN) ?? false)
    );
  }
}
