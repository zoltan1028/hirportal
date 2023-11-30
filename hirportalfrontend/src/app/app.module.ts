import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
//manually added modules
import { RouterModule, Routes } from '@angular/router';
//http client module
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooldalComponent } from './fooldal/fooldal.component';
//manually added w ng add @ng-bootstrap/ng-bootstrap
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SzerkesztesComponent } from './szerkesztes/szerkesztes.component';
import { HirComponent } from './hir/hir.component';
import { SzerkesztesitemComponent } from './szerkesztesitem/szerkesztesitem.component';
import { UjComponent } from './uj/uj.component';
//ngmodel
import { FormsModule } from '@angular/forms';
import { ReszletesComponent } from './reszletes/reszletes.component';
import { ManageszerkesztoComponent } from './manageszerkeszto/manageszerkeszto.component';
import { ModaldeleteComponent } from './modaldelete/modaldelete.component';



const routes: Routes = [
  {path: '', redirectTo: 'fooldal', pathMatch: 'full'},
  {path: 'fooldal', component: FooldalComponent},
  //{path: 'szerkesztes', component: SzerkesztesComponent, children: [{path:':id', component: UjComponent}]}
  {path: 'szerkesztes', component: SzerkesztesComponent},
  {path: 'szerkesztes/:id', component: UjComponent},
  {path: 'ujhir', component: UjComponent},
  {path: 'fooldal/:id', component: ReszletesComponent},
  {path: 'manageszerkesztok', component: ManageszerkesztoComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    FooldalComponent,
    SzerkesztesComponent,
    HirComponent,
    SzerkesztesitemComponent,
    UjComponent,
    ReszletesComponent,
    ManageszerkesztoComponent,
    ModaldeleteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    NgbModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
