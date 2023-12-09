import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { HirportalApiService } from '../hirportal.api.service';
import { Szerkeszto } from '../model/Szerkeszto';
import { SzerkesztoDto } from '../model/SzerkesztoDto';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModaldeleteComponent } from '../modaldelete/modaldelete.component';

@Component({
  selector: 'hp-manageszerkeszto',
  templateUrl: './manageszerkeszto.component.html',
  styleUrls: ['./manageszerkeszto.component.scss']
})
export class ManageszerkesztoComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService, private modalService: NgbModal) { }
  showTemplate: boolean = false
  felhasznalonev: string = ""
  jelszo: string = ""
  nev: string = ""
  id: number|null = null
  szerkesztok!: Szerkeszto[]
  szerkeszto!: Szerkeszto
  szerkesztoToDelete!: number
  ngOnInit() {this.setData();}
  submitForm() {
    if(this.id === null) {
      this.id = null
    }
    const szerkeszto: Szerkeszto = {
      id: this.id,
      felhasznalonev: this.felhasznalonev,
      jelszo: this.jelszo,
      nev: this.nev,
      token: ""
    }
    console.log(szerkeszto)
    if (szerkeszto.id === null) {
      this.apiService.postSzerkeszto(this.authService.getToken(), szerkeszto).subscribe(response => {console.log(response); this.ngOnInit();})
    } else {
      if(this.authService.getName() === szerkeszto.felhasznalonev) {szerkeszto.token = this.authService.getToken()}
      this.apiService.putSzerkeszto(this.authService.getToken(), szerkeszto).subscribe(response => {console.log(response); this.ngOnInit();})
    }
  }
  setData() {
    this.apiService.getSzerkesztok(this.authService.getToken()).subscribe(szerkesztok => {
      this.szerkesztok = szerkesztok
      const emptyoption: Szerkeszto = {
        id: null,
        felhasznalonev:"",
        jelszo: "",
        nev: "",
        token: ""
      }
      this.szerkesztok.push(emptyoption)
      this.showTemplate = true
    })
  }
  removeSzerkeszto() {
    if(this.szerkesztoToDelete !== null) {
      const szerkeszto = this.szerkesztok.find(sz => sz.id === this.szerkesztoToDelete)!.nev;
      let modal = this.modalService.open(ModaldeleteComponent, {backdrop:'static', centered: true});
      (modal.componentInstance as ModaldeleteComponent)
      .initModalDeleteWindow({receivedelement: szerkeszto, routefrommodal : '/manageszerkesztok'}, {del: () => {modal.close(); this.apiService.deleteSzerkeszto(this.authService.getToken(), this.szerkesztoToDelete).subscribe(response => {console.log(response);this.ngOnInit();})}, cancel: () => {modal.close();}});
    }
  }
  onOptionChangeSetSelectedNevAndId(id: number|null) {
    const sz = this.szerkesztok.find(sz => sz.id === id)
    this.nev = sz!.nev
    this.id = sz!.id
  }
}
