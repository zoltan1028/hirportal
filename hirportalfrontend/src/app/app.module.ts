import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooldalComponent } from './fooldal/fooldal.component';

const routes: Routes = [
  {path: '', redirectTo: 'fooldal', pathMatch: 'full'},
  {path: 'fooldal', component: FooldalComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    FooldalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
