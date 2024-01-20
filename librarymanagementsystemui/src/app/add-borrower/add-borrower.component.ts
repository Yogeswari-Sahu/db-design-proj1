import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Borrower } from '../interfaces/borrower';
import { LmsService } from '../lms.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-add-borrower',
  templateUrl: './add-borrower.component.html',
  styleUrls: ['./add-borrower.component.css']
})
export class AddBorrowerComponent implements OnInit {
  borrower: Borrower = { cardId: '', ssn: '', bname: '', address: '', phone: '' };
  errorMessage: string = '';
  showSuccessAlert = false;
  requiredFieldAlert = false;
  showDangerAlert = false;
  createdBorrowerID = '';

  @Input()
  selectedTab: string = '';

  @Output()
  childEvent = new EventEmitter<string>();

  constructor(private modalService: NgbModal, private lmsService: LmsService) { }

  ngOnInit(): void {
  }

  addBorrower(form: NgForm) {
    if (this.borrower.address === '' || this.borrower.bname === '' || this.borrower.phone === '' || this.borrower.ssn === '') {
      this.requiredFieldAlert = true;
      setTimeout(() => {
        this.requiredFieldAlert = false;
      }, 5000);
    } else {
      this.lmsService.addBorrower(this.borrower).subscribe(
        data => {
          this.createdBorrowerID = data.cardId;
          console.log('Borrower added successfully:', data);
          this.showSuccessAlert = true;
          setTimeout(() => {
            this.showSuccessAlert = false;
          }, 5000);
          form.reset();
        },
        error => {
          console.log("Error part: ",this.borrower);
          this.borrower.ssn = '';
          this.showDangerAlert = true;
          setTimeout(() => {
            this.showDangerAlert = false;
          }, 5000);
        }
      );
    }
  }
}
