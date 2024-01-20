import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {  

  constructor() {} 

  ngOnInit(): void {
  }

  @Input() 
  selectedTab:string='';

  @Output() 
  childEvent = new EventEmitter<string>();

  // Function to select a tab and emit the selected tab to the parent component
  selectTab(tabName: string): void {
    this.childEvent.emit(tabName);
  }

}
