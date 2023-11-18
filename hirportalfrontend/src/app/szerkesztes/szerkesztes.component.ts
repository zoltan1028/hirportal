import { Component, Input } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'hp-szerkesztes',
  templateUrl: './szerkesztes.component.html',
  styleUrls: ['./szerkesztes.component.scss']
})
export class SzerkesztesComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) {}
  szerkeszto!: boolean;
  hirek!: Hir[];
  ngOnInit() {
    console.log("nginit")
    console.log(this.authService.getToken());
    this.apiService.getHirekVedett(this.authService.getToken()).subscribe(hirek => {
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
