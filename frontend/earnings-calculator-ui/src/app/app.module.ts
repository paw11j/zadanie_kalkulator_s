import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {CalculatorComponent} from './calculator/calculator.component';
import {HttpClientModule} from "@angular/common/http";
import {CalculatorService} from "./calculator/calculator.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [ // komponenty
    AppComponent,
    CalculatorComponent
  ],
  imports: [ // moduły
    BrowserModule,
    HttpClientModule,

    FormsModule,
    ReactiveFormsModule
  ],
  providers: [CalculatorService], //serwisy
  bootstrap: [AppComponent]
})
export class AppModule {
}
