import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LmsService } from '../lms.service';

@Component({
  selector: 'app-refresh-fine',
  templateUrl: './refresh-fine.component.html',
  styleUrls: ['./refresh-fine.component.css']
})
export class RefreshFineComponent implements OnInit {
  @Input() 
  selectedTab:string='';

  showSuccessAlert=false;

  @Output() 
  childEvent = new EventEmitter<string>();

  constructor(private lmsService: LmsService) { }

  ngOnInit(): void {
  }

  refreshFines():void{
    this.lmsService.refreshFines().subscribe();
    console.log("Refreshed fines");
    setTimeout(()=>{
    this.showSuccessAlert=true;
    },2000);
    setTimeout(()=>{
      this.showSuccessAlert=false;
    },5000);
  }

}
