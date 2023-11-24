import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';
import { AuthenticationService } from '../authentication.service';
import { HirFoOldal } from '../model/HirFoOldal';

@Component({
  selector: 'hp-szerkesztes',
  templateUrl: './szerkesztes.component.html',
  styleUrls: ['./szerkesztes.component.scss']
})
export class SzerkesztesComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) {}
  osszesHir!: Hir[];
  idOfEnabledBoxes = new Set();
  foOldalHirek!: HirFoOldal[];
  foOldalIds: number[] = [];
  vezercikkid: number = -1;
  ngOnInit() {
    this.apiService.getFoOldalIds(this.authService.getToken()).subscribe(fohirek => {
      this.foOldalHirek = fohirek;
      for(let i of this.foOldalHirek) {
        this.foOldalIds.push(i.hir.id!);
        if(i.vezercikk) {
          this.vezercikkid = i.hir.id!
        }
    }})
    this.apiService.getHirekVedett(this.authService.getToken()).subscribe(hirek => {this.osszesHir = hirek})
  }
  @Input()
  hir!: number | null;
  get Hir() {
    return (this.hir)
  }
  initCheckBoxes(id: number) {
    for (let idf of this.foOldalIds) {if(id === idf) {return true}}
    return false
  }
  onCheckBoxUpdates(event: any, hirid: any) {
    if(event == false) {this.foOldalIds.push(hirid)}
    else if(this.foOldalIds.length > 0) {const i = this.foOldalIds.indexOf(hirid); this.foOldalIds.splice(i, 1);}
  }
  submitHirekToFoOldal() {
    this.foOldalIds.push(this.vezercikkid);
    console.log(this.foOldalIds.toString())
    this.apiService.postFoOldal(this.foOldalIds.toString(), this.authService.getToken()).subscribe(response => {console.log(response)})
    this.foOldalIds.pop()
  }
  onMarkedAsVezerCikk(event: any, hirid: any) {
    this.vezercikkid = hirid;
  }
}
