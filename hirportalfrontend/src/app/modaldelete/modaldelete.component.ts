import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'hp-modaldelete',
  templateUrl: './modaldelete.component.html',
  styleUrls: ['./modaldelete.component.scss']
})
export class ModaldeleteComponent {
  element!: string;
  routefrommodal!: string;
  ngOnInit() {}
  @Output()
  cancel = new EventEmitter<void>();
  del = new EventEmitter<void>();

  initModalDeleteWindow(input: {receivedelement : string, routefrommodal : string}, output: { del: (...args: any[]) => any, cancel: (...args: any[]) => any } ) {
    this.routefrommodal = input['routefrommodal'];
    this.element = input['receivedelement'];
    this.del.subscribe(output['del'])
    this.cancel.subscribe(output['cancel'])
  }
}
