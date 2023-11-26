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
  id: string = ""
  szerkesztok!: Szerkeszto[]
  szerkeszto!: Szerkeszto
  szerkesztoToDelete!: number
  //admin: boolean = false
  ngOnInit() {this.setData();}
  submitForm() {
    const sz: SzerkesztoDto = {
      nev: this.nev,
      jelszo: this.jelszo,
      felhasznalonev: this.felhasznalonev,
      id: null
    }
    console.log(sz.felhasznalonev + sz.nev)
    this.apiService.postSzerkeszto(this.authService.getToken(), sz).subscribe(response => {console.log(response)})
  }
  setData() {
    this.apiService.getSzerkesztok(this.authService.getToken()).subscribe(szerkesztok => {
      console.log("dasasd")

      this.szerkesztok = szerkesztok
      console.log(this.szerkesztok)
      this.showTemplate = true
    })
  }
  removeSzerkeszto() {
    this.apiService.deleteSzerkeszto(this.authService.getToken(), this.szerkesztoToDelete).subscribe(response => {console.log(response)})
  }
}
