package com.yogeswari.librarymanagementsystem.repo;

import com.yogeswari.librarymanagementsystem.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepo extends JpaRepository<Borrower, Long> {
    public Borrower findBySsn(String ssn);
    public Borrower findByCardIdContainsIgnoreCase(String cardId);

    Borrower findTopByOrderByCardIdDesc();

    Boolean existsBorrowerByCardId(String cardId);
}
