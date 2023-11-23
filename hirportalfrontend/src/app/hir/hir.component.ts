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
  hir!: Hir;
  get Hir() {
    return (this.hir)
  }
  @Input()
  vezcikk: boolean = true;
  get VezCikk() {
    return (this.vezcikk)
  }
}
