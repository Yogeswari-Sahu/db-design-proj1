package com.yogeswari.librarymanagementsystem.repo;

import com.yogeswari.librarymanagementsystem.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorsRepo extends JpaRepository<Authors,Long> {
    public List<Authors> findAllByNameContainingIgnoreCase(String name);
}
