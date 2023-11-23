import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private static token: string = ""
  private static userid: string = ""
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
  setUserId(userid: string) {
    AuthenticationService.userid = userid;
  }
  getUserId() {
    return AuthenticationService.userid;
  }
  getToken(): string {
    return AuthenticationService.token;
  }

}
