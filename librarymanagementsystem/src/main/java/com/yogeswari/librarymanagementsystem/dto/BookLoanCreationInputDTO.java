package com.yogeswari.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookLoanCreationInputDTO {
    private String cardId;
    private String isbn;
}
