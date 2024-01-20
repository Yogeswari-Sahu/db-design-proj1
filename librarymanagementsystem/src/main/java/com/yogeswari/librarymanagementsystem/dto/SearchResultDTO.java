package com.yogeswari.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class SearchResultDTO {
    private String isbn;
    private String title;
    private List<String> authors;
    private boolean available;

}
