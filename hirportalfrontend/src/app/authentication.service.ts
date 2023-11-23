import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private static token: string = ""
  constructor() { }

  emptyToken() {
    AuthenticationService.token = "";
  }
  setToken(token: string) {
    if (token === null) {
      AuthenticationService.token = "";
    } else {
      AuthenticationService.token = token;
    }
  }
  getToken(): string {
    return AuthenticationService.token;
  }

}
