import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { LmsService } from '../lms.service';
import { BookLoanSearchDTO } from '../interfaces/book-loan-search-dto';

@Component({
  selector: 'app-checkin',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
export class CheckinComponent implements OnInit {
  @Input() 
  selectedTab:string='';

  @Output() 
  childEvent = new EventEmitter<string>();


  searchResults:BookLoanSearchDTO[]=[];
  searchQuery: string = '';

  showSuccessAlert = false;
  showDangerAlert = false;

  constructor(private lmsService: LmsService) { }

  ngOnInit(): void {
    
  }

  onSearchInput(): void {
    if (this.searchQuery.trim() !== '') {
      this.lmsService.findBookLoans(this.searchQuery.trim())
        .subscribe(Response => this.searchResults = Response);
      console.log("Search of query came " + this.searchQuery);
      setTimeout(() => {
        if (this.searchResults.length == 0) {
          this.showDangerAlert = true;
          setTimeout(() => {
            this.showDangerAlert = false;
          }, 5000);
        }
      }, 3000);
    } else {
      this.searchResults = [];
      console.log("No Search results" + this.searchQuery);
    }
  }

  checkInBook(result:BookLoanSearchDTO):void{
    console.log("Providing loan id to service function while checking in:",result.loanId);
    this.lmsService.checkInBook(result.loanId)
    .subscribe(data=>{console.log("Checked in book: ",data.isbn);});
    this.showSuccessAlert = true;
        setTimeout(() => {
          this.showSuccessAlert = false;
        }, 5000);
    const objIndex=this.searchResults.indexOf(result);
    this.searchResults.splice(objIndex,1); 

  }



}
