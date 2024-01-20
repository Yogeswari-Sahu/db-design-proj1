package com.yogeswari.librarymanagementsystem.Controller;

import com.yogeswari.librarymanagementsystem.dto.*;
import com.yogeswari.librarymanagementsystem.model.Book_Loans;
import com.yogeswari.librarymanagementsystem.model.Borrower;
import com.yogeswari.librarymanagementsystem.model.Fines;
import com.yogeswari.librarymanagementsystem.service.LibraryManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/lms")
public class LibraryManagementController {
    private final LibraryManagementService libraryManagementService;

    public LibraryManagementController(LibraryManagementService libraryManagementService) {
        this.libraryManagementService = libraryManagementService;
    }

    @GetMapping("/checkAvailability/{isbn}")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable("isbn") String isbn) {
        Boolean availabilityBool = libraryManagementService.isBookAvailable(isbn);
        return new ResponseEntity<>(availabilityBool, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAuthorsFromIsbn/{isbn}")
    public ResponseEntity<List<String>> getAuthorsForBook(@PathVariable("isbn") String isbn){
        List<String> authorsList=libraryManagementService.getAuthorsForBook(isbn).stream().toList();
        return new ResponseEntity<>(authorsList,HttpStatus.OK);
    }

    @GetMapping("/searchBySubstring/{substring}")
    public ResponseEntity<List<SearchResultDTO>> searchQuery(@PathVariable("substring") String query){
        List<SearchResultDTO> resultList=libraryManagementService.searchResultDTOList(query);
        return new ResponseEntity<>(resultList,HttpStatus.OK);
    }

    //Functionality for check out by isbn
    @GetMapping("/searchByIsbn/{isbn}")
    public ResponseEntity<List<SearchResultDTO>> searchByIsbnCheckout(@PathVariable("isbn") String isbn){
        List<SearchResultDTO> resultDtoIsbnCheckout=libraryManagementService.getSearchByIsbn(isbn);
        return new ResponseEntity<>(resultDtoIsbnCheckout,HttpStatus.OK);
    }

    @GetMapping("searchBookByIsbnForCheckout/{isbn}")
    public ResponseEntity<SearchResultDTO> searchBookByIsbnCheckout(@PathVariable("isbn") String isbn){
        List<SearchResultDTO> resultDtoIsbnCheckout=libraryManagementService.getSearchByIsbn(isbn);
        return new ResponseEntity<>(resultDtoIsbnCheckout.get(0),HttpStatus.OK);
    }

    @GetMapping("/checkOutNoOfBooksCheckedOut/{cardID}")
    public ResponseEntity<Integer> checkIfCheckedOutMoreThanThreeBooks(@PathVariable("cardID") String cardID){
        Integer checkedOutNumber = libraryManagementService.checkOutNoOfBooksCheckedOut(cardID);
        return new ResponseEntity<>(checkedOutNumber, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addBorrower")
    public ResponseEntity<Borrower> addBorrower(@RequestBody Borrower borrower) {
        Borrower newborrower = libraryManagementService.createBorrower(borrower);
        return new ResponseEntity<>(newborrower, HttpStatus.CREATED);
    }
//    @PostMapping("/addBookLoan")
//    public ResponseEntity<Book_Loans> createBookLoan(@RequestBody BookLoanCreationInputDTO bookLoanCreationDto){
//        Book_Loans newBookLoan = libraryManagementService.createBookLoan(bookLoanCreationDto);
//        return new ResponseEntity<>(newBookLoan, HttpStatus.CREATED);
//    }

    @PostMapping("/createBookLoan")
    public ResponseEntity<List<Book_Loans>> createBookLoan(@RequestBody List<BookLoanCreationInputDTO> bookLoanCreationDto){
        List<Book_Loans> newBookLoan = libraryManagementService.createBookLoan(bookLoanCreationDto);
        return new ResponseEntity<>(newBookLoan, HttpStatus.CREATED);
    }

    @PutMapping("/refreshFines")
    public ResponseEntity<Void> refreshFines(){
        libraryManagementService.refreshFines();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findBookLoans/{searchQuery}")
    public ResponseEntity<List<BookLoanSearchDTO>> findBookLoans(@PathVariable("searchQuery") String searchQuery){
        List<BookLoanSearchDTO> resultList=libraryManagementService.findBookLoans(searchQuery);
        return new ResponseEntity<>(resultList,HttpStatus.OK);
    }
    @PutMapping("/checkInBook")
    public ResponseEntity<Book_Loans> checkInBook(@RequestBody Long loanId){
        Book_Loans bookLoan=libraryManagementService.checkInBook(loanId);
        return new ResponseEntity<>(bookLoan,HttpStatus.OK);
    }

    @GetMapping("/displayAllFines")
    public ResponseEntity<List<DisplayAllFinesDTO>> displayAllFines(){
        List<DisplayAllFinesDTO> allFines=libraryManagementService.displayAllFines();
        return new ResponseEntity<>(allFines,HttpStatus.OK);
    }

    @GetMapping("/displayPendingFines/{cardID}")
    public ResponseEntity<List<PendingAndPaidFinesDTO>> displayPendingFines(@PathVariable("cardID") String cardID){
        List<PendingAndPaidFinesDTO> pendingFines = libraryManagementService.displayAllPendingFines(cardID);
        return new ResponseEntity<>(pendingFines, HttpStatus.OK);
    }

    @GetMapping("/displayPaidFines/{cardID}")
    public ResponseEntity<List<PendingAndPaidFinesDTO> > displayPaidFines(@PathVariable("cardID") String cardID){
        List<PendingAndPaidFinesDTO> paidFines = libraryManagementService.displayPreviouslyPaidFines(cardID);
        return new ResponseEntity<>(paidFines, HttpStatus.OK);
    }

    @GetMapping("/PendingFineAmount/{cardID}")
    public ResponseEntity<BigDecimal> PendingFineAmount(@PathVariable("cardID") String cardID){
        BigDecimal pendingFee = libraryManagementService.PendingFineAmount(cardID);
        return new ResponseEntity<>(pendingFee, HttpStatus.OK);
    }

    //Pay Fines for the borrower
    @PutMapping("/payFines")
    public ResponseEntity<List<Fines>> payFines(@RequestBody String cardID){
        List<Fines> paidFines = libraryManagementService.payAllFines(cardID);
        return new ResponseEntity<>(paidFines, HttpStatus.OK);
    }

    //Check if borrower with the card ID exists or not
    @GetMapping("/borrowerWithCardIdExists/{cardID}")
    public ResponseEntity<Boolean> borrowerWithCardIdExists(@PathVariable("cardID") String cardID){
        Boolean borrowerWithCardIdExists = libraryManagementService.borrowerWithCardIdExists(cardID);
        return new ResponseEntity<>(borrowerWithCardIdExists, HttpStatus.OK);
    }
}
