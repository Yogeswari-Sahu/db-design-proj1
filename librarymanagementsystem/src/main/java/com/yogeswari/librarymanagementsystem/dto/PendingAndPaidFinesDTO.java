package com.yogeswari.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PendingAndPaidFinesDTO {

    private Long loanId;
    private String isbn;
    private String bookTitle;
    private BigDecimal fineAmt;
    private LocalDate dueDate;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private boolean bookOut;

}
