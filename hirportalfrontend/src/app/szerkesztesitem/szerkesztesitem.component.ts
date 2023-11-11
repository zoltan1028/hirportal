import { Component, Input } from '@angular/core';
import { Hir } from '../model/Hir';

@Component({
  selector: 'hp-szerkesztesitem',
  templateUrl: './szerkesztesitem.component.html',
  styleUrls: ['./szerkesztesitem.component.scss']
})
export class SzerkesztesitemComponent {
  ngOnInit() {
  }
  @Input()
  hir!: Hir;
  get Hir() {
    return (this.hir)
  }
}
