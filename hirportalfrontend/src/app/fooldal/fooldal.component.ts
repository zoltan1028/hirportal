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
}
