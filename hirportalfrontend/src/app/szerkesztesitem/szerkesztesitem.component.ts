import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Hir } from '../model/Hir';

@Component({
  selector: 'hp-szerkesztesitem',
  templateUrl: './szerkesztesitem.component.html',
  styleUrls: ['./szerkesztesitem.component.scss']
})
export class SzerkesztesitemComponent {
  ngOnInit() {}
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
  @Input()
  isMarked: boolean = false;
  get IsMarked() {
    return (this.isMarked)
  }

  @Output()
  onChecked: EventEmitter<void> = new EventEmitter();
  onCheckBoxEmit(value: any) {this.onChecked.emit(value);}
  @Output()
  onMarked: EventEmitter<void> = new EventEmitter();
  onMarkedEmit(value: any) {this.onMarked.emit(value);}
}
