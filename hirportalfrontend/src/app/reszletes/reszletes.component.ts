import { Component } from '@angular/core';
import { Hir } from '../model/Hir';
import { HirportalApiService } from '../hirportal.api.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'hp-reszletes',
  templateUrl: './reszletes.component.html',
  styleUrls: ['./reszletes.component.scss']
})
export class ReszletesComponent {
  constructor(private apiService: HirportalApiService, private route: ActivatedRoute) {

  }
  hirbyid!: Hir;
  ngOnInit() {
    this.apiService.getHirById(Number(this.route.snapshot.paramMap.get("id"))).subscribe(response => {this.hirbyid = response, console.log(this.hirbyid)});
  }

}
