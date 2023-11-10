import { Component, Input } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';

@Component({
  selector: 'hp-szerkesztes',
  templateUrl: './szerkesztes.component.html',
  styleUrls: ['./szerkesztes.component.scss']
})
export class SzerkesztesComponent {
  constructor(private apiService: HirportalApiService) {}
  szerkeszto!: boolean;
  hirek!: Hir[];
  ngOnInit() {
    console.log("nginit")
    this.apiService.getHirek().subscribe(hirek => {
      this.hirek = hirek
    })
  }
  dummyLogin() {
    this.szerkeszto = !this.szerkeszto;
    console.log(this.hirek);
  }
  @Input()
  id!: number | null;
  get Id() {
    return (this.id)
  }
  @Input()
  cim!: string;
  get Cim() {
    return (this.cim)
  }
  @Input()
  lejarat!: string;
  get Lejarat() {
    return (this.lejarat)
  }
  @Input()
  szoveg!: string;
  get Szoveg() {
    return (this.szoveg)
  }
}
