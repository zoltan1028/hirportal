import { Component } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Hir } from '../model/Hir';

@Component({
  selector: 'hp-fooldal',
  templateUrl: './fooldal.component.html',
  styleUrls: ['./fooldal.component.scss']
})
export class FooldalComponent {
  constructor(private apiService: HirportalApiService) {}
  szerkeszto!: boolean;
  hirek!: Hir[];
  hir!: Hir;
  searchfield!: string;
  filteredHirek!: Hir[];
  selectedCategory: string = ""
  isToggled: boolean = false
  ngOnInit() {
    console.log("nginit")
    this.apiService.getHirek().subscribe(hirek => {
      this.hirek = hirek
      this.filteredHirek = hirek
    })
  }
  dummyLogin() {
    this.szerkeszto = !this.szerkeszto;
    console.log(this.hirek);
  }
  searchInHirek(value: string) {
    if(this.isToggled) {
      this.filteredHirek = this.hirek.filter(hir => hir.cim.toLowerCase().includes(value.toLowerCase()));
    } else {
      this.filteredHirek = this.filteredHirek.filter(hir => hir.cim.toLowerCase().includes(value.toLowerCase()));
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
