import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DisplayAllFinesDto } from '../interfaces/display-all-fines-dto';
import { LmsService } from '../lms.service';
import { PendingAndPaidFinesDTO } from '../interfaces/pending-and-paid-fines-dto';

@Component({
  selector: 'app-pay-fine',
  templateUrl: './pay-fine.component.html',
  styleUrls: ['./pay-fine.component.css']
})
export class PayFineComponent implements OnInit {
  @Input()
  selectedTab: string = '';

  showSuccessAlert = false;
  showDangerAlert = false;
  showFailureAlert = false;
  displayAllFines = false;
  payFinesforBorrower = false;
  searchFinesforBorrowerSection = false;
  borrowerExists = false;

  searchQuery: string = '';
  displaySearchFines: DisplayAllFinesDto[] = [];
  pendingFinesSearchResults: PendingAndPaidFinesDTO[] = [];
  paidFinesSearchResults: PendingAndPaidFinesDTO[] = [];

  totalPendingFees: number = 0;

  @Output()
  childEvent = new EventEmitter<string>();
  constructor(private lmsService: LmsService) { }

  ngOnInit(): void {
  }

  selectTemplate(template: string): void {
    if (template === "displayAllFines") {
      this.displayAllFines = true;
      this.payFinesforBorrower = false;
      this.lmsService.displayAllFines().subscribe
        (data => this.displaySearchFines = data,
          error => {
            console.error(error);
          });
    }
    else {
      this.payFinesforBorrower = true;
      this.displayAllFines = false;
    }
  }

  payFines(): void {
    if (this.searchQuery.trim() !== '') {
      this.lmsService.borrowerWithCardIdExists(this.searchQuery).subscribe(
        data => { this.borrowerExists = data; }
      );
      setTimeout(()=>{
        
      },2000);
      console.log("Borrower exists:", this.borrowerExists);
      setTimeout(()=>{
      if (this.borrowerExists) {
        this.lmsService.payFine(this.searchQuery).subscribe();
        this.showSuccessAlert = true;
        setTimeout(() => {
          this.showSuccessAlert = false;
        }, 3000);
        this.searchFinesForBorrower();
      } else {
        this.showDangerAlert = true;
        setTimeout(() => {
          this.showDangerAlert = false;
        }, 3000);
      }
      this.borrowerExists = false;
    },3000);
    } else {
      this.showFailureAlert = true;
      setTimeout(() => {
        this.showFailureAlert = false;
      }, 5000);
    }
  }

  searchFinesForBorrower(): void {
    if (this.searchQuery.trim() !== '') {
      this.lmsService.borrowerWithCardIdExists(this.searchQuery).subscribe(
        data => { this.borrowerExists = true }
      );
      setTimeout(()=>{
        if (this.borrowerExists) {
        this.lmsService.PendingFineAmount(this.searchQuery).subscribe(data => this.totalPendingFees = data);
        
        this.searchFinesforBorrowerSection = true;        
        this.lmsService.displayPaidFines(this.searchQuery).subscribe(data => this.paidFinesSearchResults = data);
        this.lmsService.displayPendingFines(this.searchQuery).subscribe(data => this.pendingFinesSearchResults = data);
        console.log("PendingFineAmounts ",this.pendingFinesSearchResults);
      } else {
        this.showDangerAlert = true;
        setTimeout(() => {
          this.showDangerAlert = false;
        }, 5000);
      }},3000);
      
      this.borrowerExists = false;
    } else {
      this.showFailureAlert = true;
      setTimeout(() => {
        this.showFailureAlert = false;
      }, 5000);
    }
  }

}


