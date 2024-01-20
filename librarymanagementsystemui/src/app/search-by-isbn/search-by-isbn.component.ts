import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BookSearchDTO } from '../interfaces/book-search-dto';
import { LmsService } from '../lms.service';

@Component({
  selector: 'app-search-by-isbn',
  templateUrl: './search-by-isbn.component.html',
  styleUrls: ['./search-by-isbn.component.css']
})
export class SearchByIsbnComponent implements OnInit {


  searchQuery: string = '';
  searchResults: BookSearchDTO[] = [];
  isbnList: string[] = [];
  tempisbnList: string[] = [];

  @Input()
  finalIsbnList:any;

  @Input() 
  selectedTab:string='';

  @Output() 
  childEvent = new EventEmitter<string>();

  @Output() 
  dataEvent = new EventEmitter<any>();

  showSuccessAlert = false;
  showDangerAlert = false;
  showDangerAlert2 = false;
  showNoResultsAlert=false;
  showSearchingAlert=false;

  constructor(private lmsService: LmsService) { }

  onSearchInput(): void {
    this.lmsService.getSearchResultByIsbn(this.searchQuery.trim())
      .subscribe(data => this.searchResults = data);
    this.showSearchingAlert = true;
    console.log("Search of query came " + this.searchQuery);

    setTimeout(() => {
      this.showSearchingAlert = false;
    }, 1000);
    setTimeout(() => {
      if (this.searchResults.length < 1) {
        this.showNoResultsAlert = true;
        console.log("No Search results" + this.searchQuery);
        setTimeout(() => {
          this.showNoResultsAlert = false;
        }, 5000);
      }
    }, 10000);
    console.log("Search of query came " + this.searchQuery);
  }

  handleCheckboxClick(book: BookSearchDTO): void {
    const index = this.isbnList.indexOf(book.isbn);
    if (index !== -1) {
      // ISBN exists, remove it
      this.isbnList.splice(index, 1);
    } else {      
        // ISBN doesn't exist, add it
        this.isbnList.push(book.isbn);      
    }
  }

  addToCheckOutButton(): void {
    if (this.searchQuery.trim() !== '') {
      if (this.isbnList.length > 0) {
        // ISBN doesn't exist in final ISBN list, add it     
        for (var val of this.isbnList) {
          const index = this.finalIsbnList.indexOf(val);
          if (index == -1) {
            this.tempisbnList.push(val);
          }
        }
        if (this.tempisbnList.length > 0) {
          for (var val of this.tempisbnList) {
            this.finalIsbnList.push(val);
          }
          this.dataEvent.emit(this.finalIsbnList);

          this.showSuccessAlert = true;
          setTimeout(() => {
            this.showSuccessAlert = false;
          }, 5000);
          this.isbnList = [];
          this.tempisbnList = [];
          this.lmsService.getSearchResultByIsbn(this.searchQuery.trim())
            .subscribe(data => this.searchResults = data);
          
        } else {
          console.log("Temp list not more than 0");
          this.showDangerAlert2 = true;
          setTimeout(() => {
            this.showDangerAlert2 = false;
          }, 5000);
        }
      } 
      // else {
      //   this.showDangerAlert = true;
      //   setTimeout(() => {
      //     this.showDangerAlert = false;
      //   }, 5000);
      // }
    }

  }

  finalIsbnListContains(isbn:string):boolean{
    if(this.finalIsbnList.indexOf(isbn)==-1){
      return false;
    }else{
      return true;
    }
  }

  selectTab(tabName: string): void {
    this.childEvent.emit(tabName);
  }

  ngOnInit(): void {
    this.isbnList=[];
    this.tempisbnList=[];

  }


}
