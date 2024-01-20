package com.yogeswari.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "BOOK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Book implements Serializable {
    @Id
    @Column(nullable = false)
    private String isbn;
    private String title;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHORS",
            joinColumns = {
                    @JoinColumn(name = "isbn", referencedColumnName = "isbn",unique = false, updatable = true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authorId", referencedColumnName = "authorId",unique = false, updatable = true)
            }
    )
    private Set<Authors> authors;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Book_Loans> bookLoans = new HashSet<>();

}
