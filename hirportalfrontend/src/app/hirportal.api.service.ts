import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Hir } from './model/Hir';
import { Observable } from 'rxjs';
import { Kategoria } from './model/Kategoria';
import { HirFoOldal } from './model/HirFoOldal';
//import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HirportalApiService {

  private static readonly baseUrl: string = "http://localhost:4200/";
  constructor(private http: HttpClient) { }

  //Hirek
  getHirek() {
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek`)
  }
  getHirById(id: number): Observable<any> {
    return this.http.get<Hir>(`${HirportalApiService.baseUrl}hirek/${id}`)
  }


  //Token access
  getHirekVedett(token: string) {
    let headers = new HttpHeaders();
    headers = headers.append('Token', token)
    console.log(headers)
    return this.http.get<Hir[]>(`${HirportalApiService.baseUrl}hirek/vedett`, {headers})
  }
  //missing impl
  postHir(hirobj: Hir) {
    console.log(hirobj)
    return this.http.post<Hir>(`${HirportalApiService.baseUrl}hirek`, hirobj)
  }
  putHir(hirobj: Hir, pathvar: number) {
    return this.http.put<Hir>(`${HirportalApiService.baseUrl}hirek/${pathvar}`, hirobj)
  }
  postFoOldal(hirekids: string) {
    return this.http.post<void>(`${HirportalApiService.baseUrl}hirek/fooldal`, hirekids)
  }
  getFoOldalIds() {
    return this.http.get<HirFoOldal[]>(`${HirportalApiService.baseUrl}hirek/fooldalhirids`)
  }



  getKategoriak() {
    return this.http.get<Kategoria[]>(`${HirportalApiService.baseUrl}kategoriak`)
  }
  //, { responseType: 'text' } as Record<string, unknown>
  postLogin(obj: Object) {
    return this.http.post<Object>(`${HirportalApiService.baseUrl}szerkesztok`, obj, { observe: 'response' })
  }
  getLogout(felhasznalonev: string) {
    return this.http.get<void>(`${HirportalApiService.baseUrl}szerkesztok/${felhasznalonev}`)
  }
}
