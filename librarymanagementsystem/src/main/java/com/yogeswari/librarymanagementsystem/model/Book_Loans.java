package com.yogeswari.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK_LOANS")
@Entity
public class Book_Loans implements Serializable {
//    @Column(name = "loanId",nullable = false, updatable = false,unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;
    @Column(nullable = false, updatable = false)
    private LocalDate dateOut;
    @Column(nullable = false, updatable = false)
    private LocalDate dueDate;
    private LocalDate dateIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn",nullable = false, unique = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId",nullable = false, unique = false)
    private Borrower borrower;

//    @OneToOne(mappedBy = "bookLoan", cascade = CascadeType.PERSIST)
//    @PrimaryKeyJoinColumn
//    private Fines fine;

    @OneToOne(mappedBy = "bookLoan", cascade = CascadeType.ALL)
    private Fines fine;
}
