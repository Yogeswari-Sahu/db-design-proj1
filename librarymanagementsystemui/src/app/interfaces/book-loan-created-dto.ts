export interface BookLoanCreatedDTO {
    loanId:number;
    isbn:string;
    cardId:string;
    dateOut:Date;    
    dueDate:Date;
    dateIn:Date;
}
