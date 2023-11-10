import { Component, Input } from '@angular/core';

@Component({
  selector: 'hp-szerkesztesitem',
  templateUrl: './szerkesztesitem.component.html',
  styleUrls: ['./szerkesztesitem.component.scss']
})
export class SzerkesztesitemComponent {
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
