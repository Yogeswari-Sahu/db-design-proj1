export interface PendingAndPaidFinesDTO {
    
        loanId:number;      
        isbn:string;
        bookTitle:string;    
        fineAmt: number; 
        dueDate: Date;
        dateIn:Date;
        dateOut:Date;
        bookOut: boolean;    
    
}
