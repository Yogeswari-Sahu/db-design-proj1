import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Library Management System';
  
  selectedTab: string = 'home';
  isbnListToCheckOut:string[]=[];

  handleChildEvent(data: string): void {    
    this.selectedTab = data;
  }

  handleData(data: any) {
    this.isbnListToCheckOut=data;
    console.log('Data received in parent:', data);
  }
  
  

}
