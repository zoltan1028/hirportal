import { Component } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Router } from '@angular/router';
import { Hir } from '../model/Hir';
import { Kategoria } from '../model/Kategoria';
import { AuthenticationService } from '../authentication.service';
import { HirFoOldal } from '../model/HirFoOldal';
import { SzerkesztoDtoGet } from '../model/SzerkesztoDtoGet';
@Component({
  selector: 'hp-uj',
  templateUrl: './uj.component.html',
  styleUrls: ['./uj.component.scss'],
  //standalone:true,
  //imports:[FormsModule]
})
export class UjComponent {
  constructor(private apiService: HirportalApiService, private router: Router, private authService: AuthenticationService) { }
  showTemplate: boolean = false
  hir!: Hir
  id!: any
  selectedCategories!: Kategoria[]
  kategoriakOptions!: Kategoria[]
  isVezercikk!: boolean;
  hirfooldal!: HirFoOldal;
  szerkesztok!: SzerkesztoDtoGet[];
  //form props
  cim: string = '';
  lejarat: string = '2000-01-01 00:00:00';
  szoveg: string = '';
  kategoriak!: Kategoria[];
  letrehozas: string = '';
  keplink: string = '';
  ngOnInit() {this.setData()}
  setData() {
    this.apiService.getKategoriak().subscribe(kat => {this.kategoriakOptions = kat})
    this.id = this.router.url.split('/').pop()!;
    if (!(this.id === 'ujhir')) {
        this.apiService.getHirById(Number(this.id)).subscribe(hir => {
        this.showTemplate = true;
        this.hir = hir
        this.cim = this.hir.cim
        this.lejarat = this.hir.lejarat
        this.szoveg = this.hir.szoveg
        this.szerkesztok = this.hir.szerkesztok
      });
    } else {
      this.showTemplate = true;
    }
  }
  submitForm() {
    console.log(this.lejarat)

    if (this.id === "ujhir") {
      this.id = null
      this.szerkesztok = []
    }
    const hirtopost: Hir = {
      id: this.id,
      cim: this.cim,
      lejarat: this.lejarat + ' 23:59:59',
      szoveg: this.szoveg,
      kategoriak: this.selectedCategories,
      letrehozas: this.letrehozas,
      keplink: this.keplink,
      isVezercikk: this.isVezercikk,
      hirFooldal: this.hirfooldal,
      szerkesztok: this.szerkesztok
    }
    if (this.id === null) {
      this.apiService.postHir(hirtopost, this.authService.getToken()).subscribe(response => {
        console.log(response);
      });
    } else {
      this.apiService.putHir(hirtopost, this.id, this.authService.getToken()).subscribe(response => {
        console.log(response);
      });
    }
  }
}

