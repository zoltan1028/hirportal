import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private static token: string = ''
  private static username: string = ''
  private static id: string = ''
  constructor() { }
  emptyToken() {
    AuthenticationService.token = '';
  }
  setUserAuthData(token: string, name: string, id: string) {
    if (token === null) {AuthenticationService.token = '';}
    else {AuthenticationService.token = token; AuthenticationService.username = name; AuthenticationService.id = id}
  }
  getToken(): string {
    return AuthenticationService.token;
  }
  getName(): string {
    return AuthenticationService.username;
  }
  getId(): string {
    return AuthenticationService.id;
  }
}
