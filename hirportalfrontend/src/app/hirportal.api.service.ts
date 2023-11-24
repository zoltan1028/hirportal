import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Hir } from './model/Hir';
import { Observable } from 'rxjs';
import { Kategoria } from './model/Kategoria';
import { HirFoOldal } from './model/HirFoOldal';
@Injectable({
  providedIn: 'root'
})
export class HirportalApiService {
  private static readonly baseUrl: string = "http://localhost:4200/";
  constructor(private http: HttpClient) { }
  //protected
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
  postFoOldal(hirekids: string, token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.post<void>(`${HirportalApiService.baseUrl}hirek/fooldal`, hirekids, {headers})
  }
  //not protected
  getHirek() {
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek`)
  }
  getHirById(id: number): Observable<any> {
    return this.http.get<Hir>(`${HirportalApiService.baseUrl}hirek/${id}`)
  }
  getKategoriak() {
    return this.http.get<Kategoria[]>(`${HirportalApiService.baseUrl}kategoriak`)
  }
  getHirekVedett(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek/vedett`, {headers})
  }
  getFoOldalIds(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.get<HirFoOldal[]>(`${HirportalApiService.baseUrl}hirek/fooldalhirids`, {headers})
  }
  //login logout
  postLogin(obj: Object) {
    return this.http.post<Object>(`${HirportalApiService.baseUrl}szerkesztok`, obj, { observe: 'response' })
  }
  getLogout(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    return this.http.get<void>(`${HirportalApiService.baseUrl}szerkesztok`, {headers})
  }
}
//, { responseType: 'text' } as Record<string, unknown> -> if text response needed for get requests
