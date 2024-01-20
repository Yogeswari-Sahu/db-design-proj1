import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { LmsService } from '../lms.service';
import { BookLoan } from '../interfaces/book-loan';
import { BookSearchDTO } from '../interfaces/book-search-dto';
import { BookLoanCreatedDTO } from '../interfaces/book-loan-created-dto';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cardId: string = '';
  borrowerWithCardIdExistsBool = false;
  bookLoanObj: BookLoan = { cardId: '', isbn: '' };
  bookLoanDtoList: BookLoan[] = [];

  // bookLoan: BookLoanCreatedDTO = { loanId: 0, isbn: '', cardId: '', dateOut: new Date(), dueDate: new Date(), dateIn: new Date() };

  searchResults: BookSearchDTO[] = [];
  bookAvailableBool=true;
  // obj: BookLoanCreatedDTO[] = [];

  checkedOutNumOfbooks: Number = 0;

  showSuccessAlert = false;
  showDangerAlert = false;
  showAuthorDoesntExistAlert = false;

  @Input()
  selectedTab: string = '';

  @Input()
  finalIsbnList: any;

  @Output()
  dataEvent = new EventEmitter<any>();


  constructor(private lmsService: LmsService) { }

  ngOnInit(): void {
    // console.log("This final isbn list when ng onit", this.finalIsbnList);
    // for(var isbn of this.finalIsbnList){
    //   setInterval(()=>{
    //     this.lmsService.checkAvailability(isbn)
    //     .subscribe(data=>{this.bookAvailableBool=data});
    //     if(!this.bookAvailableBool){
    //       const index = this.finalIsbnList.indexOf(isbn);
    //       // Delete the row from the items array based on the index
    //       this.finalIsbnList.splice(index, 1);
    //       this.dataEvent.emit(this.finalIsbnList);
    //       // const index2 = this.searchResults.indexOf(book);
    //       // this.searchResults.splice(index2, 1);
    //     }},500);
      
    // }


    for (var isbn of this.finalIsbnList) {
      this.lmsService.getSearchResultBookByIsbnForCheckOut(isbn)
        .subscribe(data => this.searchResults.push(data));
    }
  }

  checkout(): void {
    if (this.cardId.trim() !== '') {
      this.lmsService.borrowerWithCardIdExists(this.cardId.trim())
        .subscribe(data => { this.borrowerWithCardIdExistsBool = data; });
      setTimeout(() => {
        if (this.borrowerWithCardIdExistsBool) {
          this.lmsService.checkOutNoOfBooksCheckedOut(this.cardId.trim())
            .subscribe(data => this.checkedOutNumOfbooks = data);
          setTimeout(()=>{
if ((this.checkedOutNumOfbooks + this.finalIsbnList.length) < 4) {
            if (this.finalIsbnList.length > 0) {
              for (var value of this.finalIsbnList) {
                this.bookLoanObj.cardId = this.cardId;
                this.bookLoanObj.isbn = value;
                this.bookLoanDtoList.push(this.bookLoanObj);
                console.log("Book loan while sending", this.bookLoanDtoList);
                this.bookLoanObj = { cardId: '', isbn: '' };
              }
              this.lmsService.createBookLoan(this.bookLoanDtoList)
                .subscribe(Response => {
                  console.log("Book loans created when sucess: ", Response);

                });
              this.showSuccessAlert = true;
              setTimeout(() => {
                this.showSuccessAlert = false;
              }, 5000);
              this.searchResults = [];
            }
            // for (var val in this.finalIsbnList){
            //   const index = this.finalIsbnList.indexOf(val);
            //   // Delete the row from the items array based on the index
            //   this.finalIsbnList.splice(index, 1);
            //   this.dataEvent.emit(this.finalIsbnList);
            //   const index2 = this.searchResults.indexOf(book);
            //   this.searchResults.splice(index2, 1);
            // }
            console.log("Before emiting ", this.finalIsbnList);
            for(var val in this.finalIsbnList){
              this.finalIsbnList.splice(val,1);
            }
            for(var val in this.finalIsbnList){
              this.finalIsbnList.splice(val,1);
            }
            console.log("After Splicing emiting ", this.finalIsbnList);
            this.finalIsbnList = [];
            this.dataEvent.emit(this.finalIsbnList);
            console.log("After emiting ", this.finalIsbnList);
          } else {
            this.showDangerAlert = true;
            setTimeout(() => {
              this.showDangerAlert = false;
            }, 5000);
          }
          },1000);
            
        } else {
          this.showAuthorDoesntExistAlert = true;
          setTimeout(() => {
            this.showAuthorDoesntExistAlert = false;
          }, 5000);
        }
      }, 3000);
    }
  }

  deleteRow(book: BookSearchDTO) {
    const index = this.finalIsbnList.indexOf(book.isbn);
    // Delete the row from the items array based on the index
    this.finalIsbnList.splice(index, 1);
    this.dataEvent.emit(this.finalIsbnList);
    const index2 = this.searchResults.indexOf(book);
    this.searchResults.splice(index2, 1);

  }

}
