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
  hir!: number | null;
  get Hir() {
    return (this.hir)
  }
}
