package com.yogeswari.librarymanagementsystem.repo;

import com.yogeswari.librarymanagementsystem.model.Book_Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Book_LoansRepo extends JpaRepository<Book_Loans, Long> {
    List<Book_Loans> findAllByDateInIsNull();

    List<Book_Loans> findAllByBookIsbnIsContaining(String isbn);
    List<Book_Loans> findByLoanId(Long loanId);

    List<Book_Loans> findBook_LoansByBorrower_Bname(String Bname);

    List<Book_Loans> findBook_LoansByBorrower_CardId(String cardId);
}
