package com.yogeswari.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DisplayAllFinesDTO {
    private String cardId;
    private String borrowerName;
    private BigDecimal pendingFineAmt;
}
