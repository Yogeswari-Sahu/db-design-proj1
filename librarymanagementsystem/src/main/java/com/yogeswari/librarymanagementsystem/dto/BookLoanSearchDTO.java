package com.yogeswari.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookLoanSearchDTO {
    private Long loanId;
    private String isbn;
    private String cardId;
    private LocalDate dateOut;
    private LocalDate dueDate;
    private String bookTitle;
    private String borrowerName;
}
