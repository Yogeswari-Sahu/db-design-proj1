import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Borrower } from './interfaces/borrower';
import { BookLoan } from './interfaces/book-loan';
import { BookSearchDTO } from './interfaces/book-search-dto';
import { BookLoanSearchDTO } from './interfaces/book-loan-search-dto';
import { BookLoanCreatedDTO } from './interfaces/book-loan-created-dto';
import { DisplayAllFinesDto } from './interfaces/display-all-fines-dto';
import { PendingAndPaidFinesDTO } from './interfaces/pending-and-paid-fines-dto';
import { Fines } from './interfaces/fines';
import { VoidExpression } from 'typescript';

@Injectable({
  providedIn: 'root'
})
export class LmsService {
  private apiServerUrl = environment.apiBaseURl;

  constructor(private http:HttpClient) { }

  public getSearchResult(queryString : String): Observable<BookSearchDTO[]> {
    return this.http.get<BookSearchDTO[]>(`${this.apiServerUrl}/lms/searchBySubstring/${queryString}`);
  }

  public getSearchResultByIsbn(isbn : String): Observable<BookSearchDTO[]> {
    return this.http.get<BookSearchDTO[]>(`${this.apiServerUrl}/lms/searchByIsbn/${isbn}`);
  }

  public getSearchResultBookByIsbnForCheckOut(isbn : string): Observable<BookSearchDTO> {
    return this.http.get<BookSearchDTO>(`${this.apiServerUrl}/lms/searchBookByIsbnForCheckout/${isbn}`);
  }

  public addBorrower(borrower: Borrower): Observable<Borrower> {
    return this.http.post<Borrower>(`${this.apiServerUrl}/lms/addBorrower`, borrower);
  }

  // public createBookLoan(loan: BookLoan): Observable<BookLoanCreatedDTO> {
  //   return this.http.post<BookLoanCreatedDTO>(`${this.apiServerUrl}/lms/addBookLoan`, loan);
  // }

  // BookLoanCreatedDTO

  public createBookLoan(loans: BookLoan[]): Observable<BookLoanCreatedDTO[]> {
    return this.http.post<BookLoanCreatedDTO[]>(`${this.apiServerUrl}/lms/createBookLoan`, loans);
  }

  public findBookLoans(loanQuery : String): Observable<BookLoanSearchDTO[]> {
    return this.http.get<BookLoanSearchDTO[]>(`${this.apiServerUrl}/lms/findBookLoans/${loanQuery}`);
  }

  public checkOutNoOfBooksCheckedOut(cardId:string):Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/lms/checkOutNoOfBooksCheckedOut/${cardId}`);
  }
  
  public checkInBook(loanId: number): Observable<BookLoan> {
    return this.http.put<BookLoan>(`${this.apiServerUrl}/lms/checkInBook`,loanId);
  }

  public borrowerWithCardIdExists(cardId: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiServerUrl}/lms/borrowerWithCardIdExists/${cardId}`);
  }

  public checkAvailability(isbn: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiServerUrl}/lms/checkAvailability/${isbn}`);
  }

  public payFine(cardId: string):Observable<Fines[]>{
    return this.http.put<Fines[]>(`${this.apiServerUrl}/lms/payFines`,cardId);
  }

  public displayAllFines(): Observable<DisplayAllFinesDto[]> {
    return this.http.get<DisplayAllFinesDto[]>(`${this.apiServerUrl}/lms/displayAllFines`);
  }

  public displayPendingFines(cardID:string): Observable<PendingAndPaidFinesDTO[]> {
    return this.http.get<PendingAndPaidFinesDTO[]>(`${this.apiServerUrl}/lms/displayPendingFines/${cardID}`);
  }

  public displayPaidFines(cardID:string): Observable<PendingAndPaidFinesDTO[]> {
    return this.http.get<PendingAndPaidFinesDTO[]>(`${this.apiServerUrl}/lms/displayPaidFines/${cardID}`);
  }   
  
  public PendingFineAmount(cardID:string):Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/lms/PendingFineAmount/${cardID}`);
  }

  public refreshFines():Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/lms/refreshFines`,null);
  }
}

  

  

 
