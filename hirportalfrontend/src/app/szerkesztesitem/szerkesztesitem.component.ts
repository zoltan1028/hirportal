import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  @Input()
  isChecked: boolean = false;
  get IsChecked() {
    return (this.isChecked)
  }
  @Output()
  onChecked: EventEmitter<void> = new EventEmitter();
  onCheckBoxEmit(value: any) {
    console.log(value)
    this.onChecked.emit(value);
  }
}
