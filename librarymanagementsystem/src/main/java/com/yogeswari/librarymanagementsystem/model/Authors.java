package com.yogeswari.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "AUTHORS")
@ToString
@Entity
public class Authors implements Serializable {

    @Id
    @Column(nullable = false)
    private String authorId;
    private String name;

    @ManyToMany(mappedBy = "authors",fetch = FetchType.LAZY)
    private Set<Book> books;


}
