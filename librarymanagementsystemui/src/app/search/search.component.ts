import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { LmsService } from '../lms.service';
import { BookSearchDTO } from '../interfaces/book-search-dto';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchQuery: string = '';
  searchResults: BookSearchDTO[] = [];
  isbnList: string[] = [];
  tempisbnList: string[] = [];

  @Input()
  finalIsbnList:any;

  @Input() 
  selectedTab:string='';

  @Output() 
  dataEvent = new EventEmitter<any>();

  @Output() 
  childEvent = new EventEmitter<string>();

  showSuccessAlert = false;
  showDangerAlert = false;
  showDangerAlert2 = false;
  showNoResultsAlert=false;
  showSearchingAlert=false;
  showAlreadyInCartAlert=false;
  checkboxActive=true;

  constructor(private lmsService: LmsService) { }

  onSearchInput(): void {
    this.lmsService.getSearchResult(this.searchQuery.trim())
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
      if(this.isbnList.length+this.tempisbnList.length<4){
        this.isbnList.push(book.isbn);
      }else{
        this.checkboxActive=false;
      }
      
    }
    console.log(this.isbnList);
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
          for (var obj of this.tempisbnList) {
            this.finalIsbnList.push(obj);
          }
          this.dataEvent.emit(this.finalIsbnList);

          this.showSuccessAlert = true;
          setTimeout(() => {
            this.showSuccessAlert = false;
          }, 5000);
          this.isbnList = [];
          this.tempisbnList = [];
          this.lmsService.getSearchResult(this.searchQuery.trim())
            .subscribe(data => this.searchResults = data);

        } else {
          
            this.showAlreadyInCartAlert = true;
            setTimeout(() => {
              this.showAlreadyInCartAlert = false;
            }, 5000);

          
            // this.showDangerAlert2 = true;
            // setTimeout(() => {
            //   this.showDangerAlert2 = false;
            // }, 5000);
          

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

  ngOnInit(): void {
    this.isbnList=[];
    this.tempisbnList=[];

  }

  selectTab(tabName: string): void {
    this.childEvent.emit(tabName);
  }


}
