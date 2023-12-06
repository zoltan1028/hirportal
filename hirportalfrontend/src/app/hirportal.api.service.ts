import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Hir } from './model/Hir';
import { Observable } from 'rxjs';
import { Kategoria } from './model/Kategoria';
import { HirFoOldal } from './model/HirFoOldal';
import { Szerkeszto } from './model/Szerkeszto';
import { SzerkesztoDto } from './model/SzerkesztoDto';
@Injectable({
  providedIn: 'root'
})
export class HirportalApiService {
  private static readonly baseUrl: string = "http://localhost:4200/";
  constructor(private http: HttpClient) { }
  //hirek
  getFoOldal() {
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek/fooldal`)
  }
  getHirek() {
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek`)
  }
  getHirById(id: number): Observable<any> {
    return this.http.get<Hir>(`${HirportalApiService.baseUrl}hirek/${id}`)
  }
  postHir(hirobj: Hir, token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<Hir>(`${HirportalApiService.baseUrl}hirek`, hirobj, {headers})
  }
  putHir(hirobj: Hir, pathvar: number, token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.put<Hir>(`${HirportalApiService.baseUrl}hirek/${pathvar}`, hirobj, {headers})
  }
  deleteHir(token: string, id: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.delete<Hir>(`${HirportalApiService.baseUrl}hirek/delete/${id}`, {headers})
  }
  //fooldal
  getFoOldalIds(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.get<HirFoOldal[]>(`${HirportalApiService.baseUrl}hirek/fooldalhirids`, {headers})
  }
  postFoOldal(hirekids: string, token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<void>(`${HirportalApiService.baseUrl}hirek/fooldal`, hirekids, {headers})
  }
  //szerkesztok
  getSzerkesztok(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.get<Szerkeszto[]>(`${HirportalApiService.baseUrl}szerkesztok`, {headers})
  }
  postSzerkeszto(token: string, szerkeszto: SzerkesztoDto) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<void>(`${HirportalApiService.baseUrl}szerkesztok`, szerkeszto, {headers})
  }
  putSzerkeszto(token: string, szerkeszto: SzerkesztoDto) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.put<void>(`${HirportalApiService.baseUrl}szerkesztok/${szerkeszto.id}`, szerkeszto, {headers})
  }
  deleteSzerkeszto(token: string, szerkesztodid: number) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.delete<void>(`${HirportalApiService.baseUrl}szerkesztok/${szerkesztodid}`, {headers})
  }
  //kategoriak
  getKategoriak() {
    return this.http.get<Kategoria[]>(`${HirportalApiService.baseUrl}kategoriak`)
  }
  postKategoria(token: string, kategoria: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<void>(`${HirportalApiService.baseUrl}kategoriak/${kategoria}`, null, {headers})
  }
  deleteKategoria(token: string, kategoria: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.delete<void>(`${HirportalApiService.baseUrl}kategoriak/delete/${kategoria}`, {headers})
  }
  //login
  postLogin(felhasznalonev: string, jelszo: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Felhasznalonev', felhasznalonev)
    headers = headers.append('Jelszo', jelszo)
    return this.http.post<Object>(`${HirportalApiService.baseUrl}szerkesztok/login`, null, { observe: 'response', headers: headers})
  }
  postLogout(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<void>(`${HirportalApiService.baseUrl}szerkesztok/logout`, null, {headers})
  }
}
//, { responseType: 'text' } as Record<string, unknown> -> if text response needed for get requests
