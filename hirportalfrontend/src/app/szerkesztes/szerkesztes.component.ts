import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';
import { AuthenticationService } from '../authentication.service';
import { HirFoOldal } from '../model/HirFoOldal';
import { Kategoria } from '../model/Kategoria';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModaldeleteComponent } from '../modaldelete/modaldelete.component';

@Component({
  selector: 'hp-szerkesztes',
  templateUrl: './szerkesztes.component.html',
  styleUrls: ['./szerkesztes.component.scss']
})
export class SzerkesztesComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService, private modalService: NgbModal,) {}
  osszesHir!: Hir[];
  idOfEnabledBoxes = new Set();
  foOldalHirek!: HirFoOldal[];
  foOldalIds: number[] = [];
  vezercikkid: number = -1;
  hibauzenet = false;
  ujkategoria: string = "";
  kategoriaTodelete: string = "";
  kategoriak: Kategoria[] = []
  ngOnInit() {
    this.apiService.getFoOldalIds(this.authService.getToken()).subscribe(fohirek => {
      this.foOldalHirek = fohirek;
      for(let i of this.foOldalHirek) {
        this.foOldalIds.push(i.hir.id!);
        if(i.vezercikk) {
          this.vezercikkid = i.hir.id!
        }
    }})
    this.apiService.getKategoriak().subscribe(response => {this.kategoriak = response})
    this.apiService.getHirek().subscribe(hirek => {this.osszesHir = hirek})
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
  initRadio(id: number) {
    if(id === this.vezercikkid) {return true}
    //for (let idf of this.foOldalIds) {if(id === idf) {return true}}
    return false
  }
  onCheckBoxUpdates(event: any, hirid: any) {
    if(event == false) {this.foOldalIds.push(hirid)}
    else if(this.foOldalIds.length > 0) {const i = this.foOldalIds.indexOf(hirid); this.foOldalIds.splice(i, 1);}
  }
  submitHirekToFoOldal() {
    if (this.formValidation()) {
      this.foOldalIds.push(this.vezercikkid);
      console.log(this.foOldalIds.toString())
      this.apiService.postFoOldal(this.foOldalIds.toString(), this.authService.getToken()).subscribe(response => {console.log(response)})
      this.foOldalIds.pop()
      this.hibauzenet = false
    } else {
      this.hibauzenet = true
    }
  }
  onMarkedAsVezerCikk(event: any, hirid: any) {
    this.vezercikkid = hirid;
  }
  formValidation() {
    for(let id of this.foOldalIds) {
      if (id === this.vezercikkid) {
        return true
      }
    }
    return false
  }
  deleteHir(evetn: any, hirid: any) {
    const cim = this.osszesHir.find(hir => hir.id === hirid)!.cim;
    let modal = this.modalService.open(ModaldeleteComponent, {backdrop:'static', centered: true});
    (modal.componentInstance as ModaldeleteComponent)
    .initModalDeleteWindow({receivedelement: cim, routefrommodal : '/szerkesztok'}, {del: () => {modal.close(); this.apiService.deleteHir(this.authService.getToken(), hirid).subscribe(response => {console.log(response);this.osszesHir = this.osszesHir.filter(h => h.id !== hirid);})}, cancel: () => {modal.close();}});
  }
  addUjKategoria() {
    this.apiService.postKategoria(this.authService.getToken(), this.ujkategoria).subscribe(response => {console.log(response);this.ngOnInit();
    })
  }
  removeKategoria() {
    const katnev = this.kategoriak.find(kat => kat.id === Number(this.kategoriaTodelete))!.nev;

    let modal = this.modalService.open(ModaldeleteComponent, {backdrop:'static', centered: true});
    (modal.componentInstance as ModaldeleteComponent)
    .initModalDeleteWindow({receivedelement: katnev, routefrommodal : '/szerkesztok'}, {del: () => {modal.close(); this.apiService.deleteKategoria(this.authService.getToken(), this.kategoriaTodelete).subscribe(response => {console.log(response);this.ngOnInit();})}, cancel: () => {modal.close();}});
  }
}
