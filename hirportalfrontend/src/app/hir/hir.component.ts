import { Component, Input } from '@angular/core';
import { HirportalApiService } from '../hirportal.api.service';
import { Hir } from '../model/Hir';

@Component({
  selector: 'hp-hir',
  templateUrl: './hir.component.html',
  styleUrls: ['./hir.component.scss']
})
export class HirComponent {
  ngOnInit() {
  }
  @Input()
  id!: number;
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
