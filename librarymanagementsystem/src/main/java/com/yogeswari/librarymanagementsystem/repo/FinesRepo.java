package com.yogeswari.librarymanagementsystem.repo;

import com.yogeswari.librarymanagementsystem.model.Borrower;
import com.yogeswari.librarymanagementsystem.model.Fines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinesRepo extends JpaRepository<Fines,Long> {
    public boolean existsFinesByLoanId(Long loanId);
    public Fines findByLoanId(Long loanId);

    public List<Fines> findFinesByBookLoanBorrower(Borrower br);
}
