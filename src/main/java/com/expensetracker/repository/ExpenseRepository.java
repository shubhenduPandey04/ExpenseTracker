package com.expensetracker.repository;

// Import the model and Spring repository
import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This interface gives us methods like save(), findById(), deleteById()
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // No need to write code! Spring Boot handles basic database operations.
}
