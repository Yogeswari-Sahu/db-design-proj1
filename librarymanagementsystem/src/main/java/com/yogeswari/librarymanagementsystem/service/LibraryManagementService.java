package com.yogeswari.librarymanagementsystem.service;
import com.yogeswari.librarymanagementsystem.dto.BookLoanCreationInputDTO;
import com.yogeswari.librarymanagementsystem.dto.DisplayAllFinesDTO;
import com.yogeswari.librarymanagementsystem.dto.SearchResultDTO;
import com.yogeswari.librarymanagementsystem.dto.BookLoanSearchDTO;
import com.yogeswari.librarymanagementsystem.dto.PendingAndPaidFinesDTO;
import com.yogeswari.librarymanagementsystem.model.*;
import com.yogeswari.librarymanagementsystem.repo.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class LibraryManagementService {
    private final AuthorsRepo authorsRepo;
    private final BookRepo bookRepo;
    private final Book_LoansRepo book_loansRepo;
    private final BorrowerRepo borrowerRepo;
    private final FinesRepo finesRepo;
    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementService.class);

    @Autowired
    public LibraryManagementService(AuthorsRepo authorsRepo, BookRepo bookRepo, Book_LoansRepo book_loansRepo, BorrowerRepo borrowerRepo, FinesRepo finesRepo) {
        this.authorsRepo = authorsRepo;
        this.bookRepo = bookRepo;
        this.book_loansRepo = book_loansRepo;
        this.borrowerRepo = borrowerRepo;
        this.finesRepo = finesRepo;
    }

    public boolean isBookAvailable(String isbn) {
        List<Book_Loans> loans = book_loansRepo.findAllByBookIsbnIsContaining(isbn);
        for (Book_Loans loan : loans) {
            if (loan.getDateIn() == null) {
                return false; // Book is checked out
            }
        }
        return true; // Book is available
    }

    public Set<String> getAuthorsForBook(String isbn) {
        Set<String> authorNames = new HashSet<>();
        Book book = bookRepo.findBookByIsbn(isbn);
            Set<Authors> authors = book.getAuthors();
            for (Authors author : authors) {
                authorNames.add(author.getName());
            }
        return authorNames;
    }


    public List<Book> findBooksByIsbnContaining(String isbn){
        return bookRepo.findByIsbnContainingIgnoreCase(isbn);
                //.orElseThrow(() -> new UserNotFoundException("Book with isbn " + isbn + " was not found"));
    }

    public List<Book> findBooksByTitleContaining(String title){
        return bookRepo.findBooksByTitleContainingIgnoreCase(title);
                //.orElseThrow(() -> new UserNotFoundException("Book with title " + title + " was not found"));
    }

    public List<Authors> findAuthorsByNameContaining(String authorsName){
        return authorsRepo.findAllByNameContainingIgnoreCase(authorsName);
    }

    @Transactional
    public List<SearchResultDTO> searchResultDTOList(String query){
        HashSet<SearchResultDTO> resultDTO=new HashSet<>();
        HashSet<Book> uniqueBookList=new HashSet<>();
        List<Book> booksByIsbn=findBooksByIsbnContaining(query);
        if(!booksByIsbn.isEmpty()){
            uniqueBookList.addAll(booksByIsbn);
        }
        uniqueBookList.addAll(findBooksByTitleContaining(query));
        List<Authors> authorsContainingString=findAuthorsByNameContaining(query);
        for(Authors author:authorsContainingString){
            Set<Book> books=author.getBooks();
            uniqueBookList.addAll(books);
        }
        for(Book book:uniqueBookList){
            SearchResultDTO dtoObject=new SearchResultDTO();
            dtoObject.setIsbn(book.getIsbn());
            dtoObject.setTitle(book.getTitle());
            Set<String> s=getAuthorsForBook(book.getIsbn());
            dtoObject.setAuthors(s.stream().toList());
            dtoObject.setAvailable(isBookAvailable(book.getIsbn()));
            resultDTO.add(dtoObject);
        }
        return resultDTO.stream().toList();
    }

    public Borrower createBorrower(Borrower borrower) {
        // Check for duplicate SSN
        Borrower existingBorrower = borrowerRepo.findBySsn(borrower.getSsn());
        if (existingBorrower != null) {
            throw new IllegalArgumentException("SSN already exists in the system.");
        }

        // Generate a new cardId
        String newCardId = generateNewCardId();

        // Set the new cardId and save the borrower
        borrower.setCardId(newCardId);
        return borrowerRepo.save(borrower);
    }

    //Create BookLoan when checking out a book
    public List<Book_Loans> createBookLoan(List<BookLoanCreationInputDTO> bookLoanCreationInputDTO){
        List<Book_Loans> book_loansList=new ArrayList<>();
        for(BookLoanCreationInputDTO bookLoanCreationInputObj:bookLoanCreationInputDTO){

            if(isBookAvailable(bookLoanCreationInputObj.getIsbn())){
                Book_Loans bl=new Book_Loans();
                bl.setBorrower(borrowerRepo.findByCardIdContainsIgnoreCase(bookLoanCreationInputObj.getCardId()));
                bl.setBook(bookRepo.findBookByIsbn(bookLoanCreationInputObj.getIsbn()));
                bl.setDateOut(LocalDate.now());
                bl.setDueDate(LocalDate.now().plusDays(14));
                bl.setDateIn(null);
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                book_loansList.add(book_loansRepo.save(bl));
            }
        }
        return book_loansList;
    }

//    public Book_Loans createBookLoan(BookLoanCreationInputDTO bookLoanCreationInputDTO){
//        Book_Loans bl=new Book_Loans();
//        bl.setBorrower(borrowerRepo.findByCardIdContainsIgnoreCase(bookLoanCreationInputDTO.getCardId()));
//        bl.setBook(bookRepo.findBookByIsbn(bookLoanCreationInputDTO.getIsbn()));
//        bl.setDateOut(LocalDate.now());
//        bl.setDueDate(LocalDate.now().plusDays(14));
//        bl.setDateIn(null);
//        return book_loansRepo.save(bl);
//    }

    //Find book Loans searching on any of BOOKS.isbn, BORROWER.card_no, and/or any substring of BORROWER name
    public List<BookLoanSearchDTO> findBookLoans(String searchQuery){
        HashSet<Book_Loans> bLoans=new HashSet<>();
        bLoans.addAll(book_loansRepo.findAllByBookIsbnIsContaining(searchQuery));
        bLoans.addAll(book_loansRepo.findBook_LoansByBorrower_CardId(searchQuery));
        bLoans.addAll(book_loansRepo.findBook_LoansByBorrower_Bname(searchQuery));
        HashSet<BookLoanSearchDTO> resultSet=new HashSet<>();
        for(Book_Loans bl:bLoans){
            if(bl.getDateIn()==null){
                BookLoanSearchDTO newObj=new BookLoanSearchDTO();
                newObj.setLoanId(bl.getLoanId());
                newObj.setIsbn(bl.getBook().getIsbn());
                newObj.setCardId(bl.getBorrower().getCardId());
                newObj.setDateOut(bl.getDateOut());
                newObj.setDueDate(bl.getDueDate());
                newObj.setBookTitle(bl.getBook().getTitle());
                newObj.setBorrowerName(bl.getBorrower().getBname());
                resultSet.add(newObj);
            }
        }

        return resultSet.stream().toList();
    }

    //Check In the book selected to check in
//    public Book_Loans checkInBook(Long loanId){
//        Book_Loans bl=book_loansRepo.findByLoanId(loanId).get(0);
//        bl.setDateIn(LocalDate.now());
//        try {
//            if(isBookOverdue(bl)){
//                Fines fine;
//                if(finesRepo.existsFinesByLoanId(bl.getLoanId())){
//                    fine=finesRepo.findByLoanId(bl.getLoanId());
//                    fine.setFineAmt(BigDecimal.valueOf(ChronoUnit.DAYS.between(bl.getDueDate(),LocalDate.now() )*0.25));
////                    finesRepo.save(fine);
//                } else {
//                    fine = new Fines();
//                    fine.setBookLoan(bl);
//                    fine.setLoanId(bl.getLoanId());
//                    fine.setPaid(false);
//                    fine.setFineAmt(BigDecimal.valueOf(ChronoUnit.DAYS.between(bl.getDueDate(), bl.getDateIn()) * 0.25));
//
//                    bl.setFine(fine);
////                    book_loansRepo.save(bl);
//                    if (fine.getLoanId() == null) {
//                        logger.error("loanId is null for Fines entity.");
//                    } else {
//                        logger.info("Fine entity before save: " + fine.getLoanId() + ' ' + fine.getBookLoan().getLoanId() + ' ' + fine.getBookLoan().getFine().getBookLoan() + ' ' + fine.getFineAmt());
//                    }
//
//                }
//                finesRepo.save(fine);
//            }
//        } catch (Exception e) {
//            Throwable originalException = e.getCause();
//            logger.error("An error occurred in yourMethod...."+ e);
//        }
//
//        return book_loansRepo.save(bl);
//    }


    public Book_Loans checkInBook(Long loanId){
        Book_Loans bl=book_loansRepo.findByLoanId(loanId).get(0);
        bl.setDateIn(LocalDate.now());
        try {
            if(isBookOverdue(bl)){
                this.fineCalculationFunction(loanId);
            }
        } catch (Exception e) {
            Throwable originalException = e.getCause();
            logger.error("An error occurred in yourMethod...."+ e);
        }

        return book_loansRepo.save(bl);
    }

    //Check if book is overdue or not
    public boolean isBookOverdue(Book_Loans bookLoan) {
        return bookLoan.getDateIn().isAfter(bookLoan.getDueDate());
    }

    // Generate new Card ID
    private String generateNewCardId() {
        // Find the latest cardId in the database and increment it
        Borrower latestBorrower = borrowerRepo.findTopByOrderByCardIdDesc();
        String lastCardId = latestBorrower.getCardId();
        if (lastCardId==null) {
            lastCardId = "ID000000";
        }

        // Extract the numeric part and increment
        int numericPart = Integer.parseInt(lastCardId.substring(2)) + 1;

        // Format and return the new cardId
        return String.format("ID%06d", numericPart);
    }


    public Integer checkOutNoOfBooksCheckedOut(String cardId){
        List<Book_Loans> bookLoans =book_loansRepo.findAllByDateInIsNull();
        List<Book_Loans> bookLoansFinal =new ArrayList<>();
        for(Book_Loans bl:bookLoans){
            if(bl.getBorrower().getCardId().equals(cardId)){
                bookLoansFinal.add(bl);
            }
        }
        return bookLoansFinal.size();
    }

    public List<SearchResultDTO> getSearchByIsbn(String isbn){
        HashSet<SearchResultDTO> resultDTO=new HashSet<>();
        HashSet<Book> uniqueBookList=new HashSet<>();
        List<Book> booksByIsbn=findBooksByIsbnContaining(isbn);
        if(!booksByIsbn.isEmpty()){
            uniqueBookList.addAll(booksByIsbn);
        }
        for(Book book:uniqueBookList){
            SearchResultDTO dtoObject=new SearchResultDTO();
            dtoObject.setIsbn(book.getIsbn());
            dtoObject.setTitle(book.getTitle());
            Set<String> s=getAuthorsForBook(book.getIsbn());
            dtoObject.setAuthors(s.stream().toList());
            dtoObject.setAvailable(isBookAvailable(book.getIsbn()));
            resultDTO.add(dtoObject);
        }
        return resultDTO.stream().toList();
    }

    //Check if Borrower with certain CardID Exists
    public boolean borrowerWithCardIdExists(String cardId){
        cardId = cardId.replaceAll("^\"|\"$", "");
        return borrowerRepo.existsBorrowerByCardId(cardId);
    }

    //Refresh fines
    @Transactional
    public void refreshFines(){
        List<Book_Loans> bLoansList=book_loansRepo.findAllByDateInIsNull();
        HashSet<Long> loanIdListWhereBookCheckedOut=new HashSet<>();
        for(Book_Loans bl:bLoansList){
            if(ChronoUnit.DAYS.between(bl.getDueDate(),LocalDate.now())>0){
                loanIdListWhereBookCheckedOut.add(bl.getLoanId());
            }
        }
        try {
            for(Long loanId:loanIdListWhereBookCheckedOut){
                this.fineCalculationFunction(loanId);
            }
        } catch (Exception e) {
            Throwable originalException = e.getCause();
            logger.error("An error occurred in yourMethod"+ e);
        }

    }

    public void fineCalculationFunction(Long loanId){
        if(finesRepo.existsFinesByLoanId(loanId)){
            Fines fine=finesRepo.findByLoanId(loanId);
            Book_Loans bl=book_loansRepo.findByLoanId(loanId).get(0);
            fine.setFineAmt(BigDecimal.valueOf(ChronoUnit.DAYS.between(bl.getDueDate(),LocalDate.now() )*0.25));
            finesRepo.save(fine);
        } else {
            Fines fine=new Fines();
            Book_Loans bl=book_loansRepo.findByLoanId(loanId).get(0);
            fine.setPaid(false);
            fine.setFineAmt(BigDecimal.valueOf(ChronoUnit.DAYS.between(bl.getDueDate(), LocalDate.now())*0.25));
            fine.setBookLoan(bl);
            bl.setFine(fine);

            book_loansRepo.save(bl);
            if (fine.getLoanId() == null) {
                logger.error("loanId is null for Fines entity.");
            }else{
                logger.info("Fine entity before save: " + fine.getLoanId()+' '+fine.getBookLoan()+' '+fine.getFineAmt());
            }
            finesRepo.save(fine);
        }
    }

    public BigDecimal PendingFineAmount(String cardId){
        BigDecimal sum= BigDecimal.valueOf(0.00);
        Borrower br=borrowerRepo.findByCardIdContainsIgnoreCase(cardId);
        List<Fines> finesList=finesRepo.findFinesByBookLoanBorrower(br);
        for(Fines fine:finesList){
            if(!fine.isPaid()&&fine.getBookLoan().getDateIn()!=null){
                sum=sum.add(fine.getFineAmt());
            }
        }
        return sum;
    }

    public List<DisplayAllFinesDTO> displayAllFines(){
        List<Borrower> borrowerList=borrowerRepo.findAll();
        HashSet<DisplayAllFinesDTO> result=new HashSet<>();
        for(Borrower br:borrowerList){
            DisplayAllFinesDTO obj=new DisplayAllFinesDTO();
            obj.setCardId(br.getCardId());
            obj.setBorrowerName(br.getBname());
            obj.setPendingFineAmt(PendingFineAmount(br.getCardId()));
            result.add(obj);
        }
        return result.stream().toList();
    }



    public PendingAndPaidFinesDTO finesSearchObj(Book_Loans book_loan){
        PendingAndPaidFinesDTO obj=new PendingAndPaidFinesDTO();
        obj.setLoanId(book_loan.getLoanId());
        obj.setIsbn(book_loan.getBook().getIsbn());
        obj.setBookTitle(book_loan.getBook().getTitle());
        obj.setDueDate(book_loan.getDueDate());
        obj.setDateOut(book_loan.getDateOut());
        obj.setDateIn(book_loan.getDateIn());
        obj.setBookOut(!isBookAvailable(book_loan.getBook().getIsbn()));
        obj.setFineAmt(book_loan.getFine().getFineAmt());
        return obj;
    }

    public List<PendingAndPaidFinesDTO> displayAllPendingFines(String cardId){
        HashSet<PendingAndPaidFinesDTO> res=new HashSet<>();
        List<Book_Loans> book_loansList=book_loansRepo.findBook_LoansByBorrower_CardId(cardId);
        for(Book_Loans bl:book_loansList){
            if(bl.getFine()!=null){
                if(!bl.getFine().isPaid()){
                    res.add(finesSearchObj(bl));
                }
            }
        }
        return res.stream().toList();
    }

    public List<PendingAndPaidFinesDTO> displayPreviouslyPaidFines(String cardId){
        HashSet<PendingAndPaidFinesDTO> res=new HashSet<>();
        List<Book_Loans> book_loansList=book_loansRepo.findBook_LoansByBorrower_CardId(cardId);
        for(Book_Loans bl:book_loansList){
            if(bl.getFine()!=null){
                if(bl.getFine().isPaid()){
                    res.add(finesSearchObj(bl));
                }
            }
        }
        return res.stream().toList();
    }

    public List<Fines> payAllFines(String cardId){
        List<PendingAndPaidFinesDTO> pendingFines=displayAllPendingFines(cardId);
        HashSet<Fines> finesPmt=new HashSet<>();
        for (PendingAndPaidFinesDTO pf:pendingFines){
           if(pf.getDateIn()!=null){
               Fines fine=finesRepo.findByLoanId(pf.getLoanId());
               fine.setPaid(true);
               finesRepo.save(fine);
               finesPmt.add(finesRepo.findByLoanId(pf.getLoanId()));
           }
        }
        return finesPmt.stream().toList();
    }
}
