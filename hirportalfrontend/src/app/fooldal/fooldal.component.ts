import { Component } from '@angular/core';

@Component({
  selector: 'hp-fooldal',
  templateUrl: './fooldal.component.html',
  styleUrls: ['./fooldal.component.scss']
})
export class FooldalComponent {
  szerkeszto!: boolean;
  ngOnInit() {
    console.log("nginit")
  }
  dummyLogin() {
    this.szerkeszto = !this.szerkeszto;
  }
}
