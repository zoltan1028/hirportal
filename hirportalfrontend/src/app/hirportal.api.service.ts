import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Hir } from './model/Hir';
import { Observable } from 'rxjs';
import { Kategoria } from './model/Kategoria';
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
  postHir(hirobj: Hir) {
    console.log(hirobj)
    return this.http.post<Hir>(`${HirportalApiService.baseUrl}hirek`, hirobj)
  }
  putHir(hirobj: Hir, pathvar: number) {
    return this.http.put<Hir>(`${HirportalApiService.baseUrl}hirek/${pathvar}`, hirobj)
  }
  //Kategoriak
  getKategoriak() {
    return this.http.get<Kategoria[]>(`${HirportalApiService.baseUrl}kategoriak`)
  }
}
