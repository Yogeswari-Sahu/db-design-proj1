export interface BookLoanSearchDTO {
    loanId:number;
    isbn:string;
    cardId:string;
    dateOut:Date;
    dueDate:Date;
    bookTitle:string;
    borrowerName:string;
}
