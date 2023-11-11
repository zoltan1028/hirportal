import { Component, Input } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Router } from '@angular/router';
import { Hir } from '../model/Hir';
import { Kategoria } from '../model/Kategoria';
@Component({
  selector: 'hp-uj',
  templateUrl: './uj.component.html',
  styleUrls: ['./uj.component.scss'],
  //standalone:true,
  //imports:[FormsModule]
})
export class UjComponent {
  constructor(private apiService: HirportalApiService, private router: Router) { }
  dataFetched: boolean = false
  hir!: Hir
  id!: any
  selectedCategories!: Kategoria[]
  kategoriakOptions!: Kategoria[]
  ngOnInit() {
    this.setData()
  }
  @Input()
  cim!: string;
  get Cim() {
    return (this.cim ?? "");
  }
  @Input()
  lejarat!: string;
  get Lejarat() {
    return (this.lejarat ?? "");
  }
  @Input()
  szoveg!: string;
  get Szoveg() {
    return (this.szoveg ?? "");
  }
  @Input()
  kategoriak!: Kategoria[];
  get Kategoriak() {
    return (this.kategoriak ?? "");
  }
  setData() {
    this.apiService.getKategoriak().subscribe(kat => {this.kategoriakOptions = kat})
    this.id = this.router.url.split('/').pop()!;
    if (!(this.id === 'ujhir')) {
      this.apiService.getHirById(Number(this.id)).subscribe(hir => {
        this.dataFetched = true;
        this.hir = hir
        this.cim = this.hir.cim
        this.lejarat = this.hir.lejarat
        this.szoveg = this.hir.szoveg
      });
    } else {
      this.dataFetched = true;
    }
  }
  submitForm() {
    if (this.id === "ujhir") {
      this.id = null
    }
    const hirtopost: Hir = {
      id: this.id,
      cim: this.Cim,
      lejarat: this.Lejarat,
      szoveg: this.Szoveg,
      kategoriak: this.selectedCategories
    }
    if (this.id === null) {
      this.apiService.postHir(hirtopost).subscribe(response => {
        console.log(response);
      });
    } else {
      this.apiService.putHir(hirtopost, this.id).subscribe(response => {
        console.log(response);
      });
    }
  }
}

