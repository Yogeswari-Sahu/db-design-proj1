package com.yogeswari.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "FINES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fines implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private Long loanId;

    @Id
    private Long loanId;

    @Column(precision = 10, scale = 2)
    private BigDecimal fineAmt;
    private boolean paid;

//
    @OneToOne
    @MapsId
    @JoinColumn(name = "loanId")
    private Book_Loans bookLoan;

}
