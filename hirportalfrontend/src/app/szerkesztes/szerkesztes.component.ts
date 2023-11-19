import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';
import { AuthenticationService } from '../authentication.service';
import { HirFoOldal } from '../model/HirFoOldal';

@Component({
  selector: 'hp-szerkesztes',
  templateUrl: './szerkesztes.component.html',
  styleUrls: ['./szerkesztes.component.scss']
})
export class SzerkesztesComponent {
  constructor(private apiService: HirportalApiService, private authService: AuthenticationService) {}
  osszesHir!: Hir[];
  foOldalHirekIds: string = ""
  idOfEnabledBoxes = new Set();
  foOldalHirek!: HirFoOldal[];
  ngOnInit() {
    this.apiService.getFoOldalIds().subscribe(fohirek => {
      this.foOldalHirek = fohirek
    })
    console.log("nginit")
    console.log(this.authService.getToken());
    this.apiService.getHirekVedett(this.authService.getToken()).subscribe(hirek => {
      this.osszesHir = hirek


    })
  }
  @Input()
  hir!: number | null;
  get Hir() {
    return (this.hir)
  }
  initCheckboxes(hirid: number) {
    for(let i of this.foOldalHirek) {if(i.hir.id === hirid) {
      this.idOfEnabledBoxes.add(hirid)
      return true
    }}
    return false
  }
  checkUpdate(id: number) {

  }
  submitHirekToFoOldal() {
    ///this.apiService.postFoOldal(this.foOldalHirekIds.slice(1)).subscribe(response => {console.log(response)})
  }
}
