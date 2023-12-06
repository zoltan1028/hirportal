import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private static token: string = ''
  private static username: string = ''
  constructor() { }
  emptyToken() {
    AuthenticationService.token = '';
  }
  setToken(token: string, name: string) {
    if (token === null) {AuthenticationService.token = '';}
    else {AuthenticationService.token = token; AuthenticationService.username = name;}
  }
  getToken(): string {
    return AuthenticationService.token;
  }
  getName(): string {
    return AuthenticationService.username;
  }
}
