import { Component } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Hir } from '../model/Hir';
import { FormGroup } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'hp-fooldal',
  templateUrl: './fooldal.component.html',
  styleUrls: ['./fooldal.component.scss']
})
export class FooldalComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) {}
  hirek!: Hir[];
  hir!: Hir;
  showSzerkesztesGombok!: boolean
  searchfield!: string;
  filteredHirek!: Hir[];
  selectedCategory: string = ""
  isToggled: boolean = false
  loginText: string = "Bejelentkezés"
  loginProperty: boolean = true
  ngOnInit() {
    console.log("nginit")
    this.apiService.getHirek().subscribe(hirek => {
      this.hirek = hirek
      this.filteredHirek = hirek
      this.showSzerkesztesGombok = !!this.authService.getToken()
    })

    if (this.authService.getToken() == "") {
      this.loginText = "Bejelentkezés"
      this.loginProperty = true

    } else {
      this.loginText = "Kijelentkezés"
      this.loginProperty = false

    }

  }
  onLogin(formdata: any) {
    console.log(this.authService.getToken())
    if (this.authService.getToken() == "") {
      const formObj = {
        felhasznalonev:formdata.felhasznalonev,
        jelszo:formdata.jelszo
      }
      this.apiService.postLogin(formObj).subscribe(response => {
        this.authService.setToken(response.headers.get('Token')!)
        this.authService.setUserId(response.headers.get('Authorization')!)
        this.showSzerkesztesGombok = !!this.authService.getToken();

        if (!!this.authService.getToken()) {
          this.loginText = "Kijelentkezés"
          this.loginProperty = false
        }
      });
    } else {
      this.apiService.getLogout(this.authService.getUserId()).subscribe(() => {
        this.authService.emptyToken();
        this.showSzerkesztesGombok = !!this.authService.getToken()
        if (this.authService.getToken() === "") {
          this.loginText = "Bejelentkezés"
          this.loginProperty = true
        }
      });

    }
  }
  searchInHirek(event: any) {
      if(this.isToggled) {
        this.filteredHirek = this.hirek.filter(hir => hir.cim.toLowerCase().includes(event.value.toLowerCase()));
      } else {
        this.filteredHirek = this.filteredHirek.filter(hir => hir.cim.toLowerCase().includes(event.value.toLowerCase()));
      }
  }
  setActiveCategory(value: string) {
    if(value === "") {
      this.filteredHirek = this.hirek
    }
    this.filteredHirek = this.hirek
    this.filteredHirek = this.filteredHirek.filter(hir => hir.kategoriak.some(k => k.nev.includes(value.toLowerCase())));
    console.log(this.filteredHirek)
  }
  setToggle() {
    this.isToggled = !this.isToggled
  }
}
