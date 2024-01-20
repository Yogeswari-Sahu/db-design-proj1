import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input() 
  selectedTab:string='';

  @Output() 
  childEvent = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  // Function to select a tab and emit the selected tab to the parent component
  selectTab(tabName: string): void {
    this.childEvent.emit(tabName);
  }
  onTabSelected(tabName: string): void {
    this.childEvent.emit(tabName);
  }

}
