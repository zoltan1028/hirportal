import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
//manually added modules
import { RouterModule, Routes } from '@angular/router';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooldalComponent } from './fooldal/fooldal.component';
//manually added w ng add @ng-bootstrap/ng-bootstrap
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SzerkesztesComponent } from './szerkesztes/szerkesztes.component';

const routes: Routes = [
  {path: '', redirectTo: 'fooldal', pathMatch: 'full'},
  {path: 'fooldal', component: FooldalComponent},
  {path: 'szerkesztes', component: SzerkesztesComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    FooldalComponent,
    SzerkesztesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    NgbModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
