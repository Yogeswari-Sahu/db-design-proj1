import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { AddBorrowerComponent } from './add-borrower/add-borrower.component';
import { PayFineComponent } from './pay-fine/pay-fine.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { SearchByIsbnComponent } from './search-by-isbn/search-by-isbn.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { RefreshFineComponent } from './refresh-fine/refresh-fine.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CheckinComponent } from './checkin/checkin.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchComponent,
    AddBorrowerComponent,
    PayFineComponent,
    HeaderComponent,
    SearchByIsbnComponent,
    CheckoutComponent,
    RefreshFineComponent,
    CheckinComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
