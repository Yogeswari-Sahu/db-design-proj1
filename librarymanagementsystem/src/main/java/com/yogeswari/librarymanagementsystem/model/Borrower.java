package com.yogeswari.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BORROWER")
@ToString
@Entity
public class Borrower implements Serializable {
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String cardId;
    @Column(unique = true, nullable = false)
    private String ssn;
    @Column(nullable = false)
    private String bname;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;

    public Borrower(String ssn, String bName, String address, String phone) {
        this.ssn = ssn;
        this.bname = bName;
        this.address = address;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "borrower")
    private Set<Book_Loans> bookLoans;
}
