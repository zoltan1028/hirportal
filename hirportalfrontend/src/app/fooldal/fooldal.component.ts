import { Component } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Hir } from '../model/Hir';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'hp-fooldal',
  templateUrl: './fooldal.component.html',
  styleUrls: ['./fooldal.component.scss']
})
export class FooldalComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) {}
  hirek!: Hir[];
  showSzerkesztesGombok!: boolean
  //fooldalon megjelenitendo hirek
  filteredHirek!: Hir[];
  isToggled: boolean = false
  loginText: string = "Bejelentkezés"
  requiredPropertForLogin: boolean = true
  loginFieldDisabled: boolean = false
  ngOnInit() {
    console.log("ngOnInit")
    this.apiService.getFoOldal().subscribe(hirek => {
      this.hirek = hirek
      this.filteredHirek = hirek
      this.onVezercikkInFilteredSetVezercikkFirst()
      this.showSzerkesztesGombok = !!this.authService.getToken()
    })
    this.initUserLoginProps();
  }
  onLoginButtonClick(formdata: any) {
    console.log(this.authService.getToken())
    //onLogin
    if (this.authService.getToken() == "") {
      this.apiService.postLogin(formdata.felhasznalonev, formdata.jelszo).subscribe(response => {

        this.authService.setToken(response.headers.get('Token')!)
        this.initUserLoginProps();
      });

    //onLogout
    } else {
      this.apiService.postLogout(this.authService.getToken()).subscribe(() => {
        this.authService.emptyToken();
        this.initUserLoginProps();
      });
    }
  }
  searchInHirek(event: any) {
      if(this.isToggled) {
        this.filteredHirek = this.hirek.filter(hir => hir.cim.toLowerCase().includes(event.value.toLowerCase()));
        this.onVezercikkInFilteredSetVezercikkFirst()
      } else {
        this.filteredHirek = this.filteredHirek.filter(hir => hir.cim.toLowerCase().includes(event.value.toLowerCase()));
        this.onVezercikkInFilteredSetVezercikkFirst()
      }
  }
  setActiveCategory(value: string) {
    if(value === "") {
      this.filteredHirek = this.hirek
      this.onVezercikkInFilteredSetVezercikkFirst()
    }
    this.filteredHirek = this.hirek
    this.filteredHirek = this.filteredHirek.filter(hir => hir.kategoriak.some(k => k.nev.includes(value.toLowerCase())));
    this.onVezercikkInFilteredSetVezercikkFirst()
  }
  setToggle() {
    this.isToggled = !this.isToggled
  }
  initUserLoginProps() {
    if (this.authService.getToken() == "") {
      this.showSzerkesztesGombok = !!this.authService.getToken();
      this.loginText = "Bejelentkezés"
      this.requiredPropertForLogin = true
      this.loginFieldDisabled = false
    } else {
      this.showSzerkesztesGombok = !!this.authService.getToken()
      this.loginText = "Kijelentkezés"
      this.requiredPropertForLogin = false
      this.loginFieldDisabled = true
    }
  }
  onVezercikkInFilteredSetVezercikkFirst() {
    var vezCikk: Hir = this.filteredHirek[0];
    var vezFound: boolean = false
    for(let hir of this.filteredHirek) {
      if(hir.hirFooldal.vezercikk === true) {vezCikk = hir; vezFound = true;}
    }
    var finalList: Hir[] = []
    if(vezFound) {
      finalList.push(vezCikk);
      for (let hir of this.filteredHirek) {
        if(!(hir === vezCikk)) {finalList.push(hir);}
      }
      //comp prop value set only if vezcikk found in filtered
      this.filteredHirek = finalList
    }
  }
}
