import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { HirportalApiService } from '../hirportal.api.service';
import { Szerkeszto } from '../model/Szerkeszto';
import { SzerkesztoDto } from '../model/SzerkesztoDto';

@Component({
  selector: 'hp-manageszerkeszto',
  templateUrl: './manageszerkeszto.component.html',
  styleUrls: ['./manageszerkeszto.component.scss']
})
export class ManageszerkesztoComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) { }
  showTemplate: boolean = false
  felhasznalonev: string = ""
  jelszo: string = ""
  nev: string = ""
  id: number|null = null
  szerkesztok!: Szerkeszto[]
  szerkeszto!: Szerkeszto
  szerkesztoToDelete!: number
  //admin: boolean = false
  ngOnInit() {this.setData();}
  submitForm() {
    if(this.id === null) {
      this.id = null
    }
    console.log(this.id + "WTF")
    const szerkeszto: SzerkesztoDto = {
      id: this.id,
      felhasznalonev: this.felhasznalonev,
      jelszo: this.jelszo,
      nev: this.nev
    }
    console.log(szerkeszto)
    if (szerkeszto.id === null) {
      this.apiService.postSzerkeszto(this.authService.getToken(), szerkeszto).subscribe(response => {console.log(response)})
    } else {
      this.apiService.putSzerkeszto(this.authService.getToken(), szerkeszto).subscribe(response => {console.log(response)})
    }
    this.ngOnInit();

  }
  setData() {
    this.apiService.getSzerkesztok(this.authService.getToken()).subscribe(szerkesztok => {
      this.szerkesztok = szerkesztok
      const emptyoption: SzerkesztoDto = {
        id: null,
        felhasznalonev:"null",
        jelszo: "null",
        nev: "null"
      }
      this.szerkesztok.push(emptyoption)
      this.showTemplate = true
    })
  }
  removeSzerkeszto() {
    if(this.szerkesztoToDelete !== null) {
      this.apiService.deleteSzerkeszto(this.authService.getToken(), this.szerkesztoToDelete).subscribe(response => {console.log(response);this.ngOnInit();})
    }
  }
  test(id: number|null) {
    const sz = this.szerkesztok.find(sz => sz.id === id)
    this.nev = sz!.nev
    this.id = sz!.id
  }
}
