package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This marks the class as a REST controller to handle HTTP requests.
@RestController

// Base URL for all endpoints in this controller: "/api/expenses"
@RequestMapping("/api/expenses")
public class ExpenseController {

    // Injects (auto-wires) the ExpenseRepository to interact with the database.
    @Autowired
    private ExpenseRepository expenseRepository;

    // 1. Add a new expense (HTTP POST request to /api/expenses)
    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        // Save the expense to the database
        Expense savedExpense = expenseRepository.save(expense);

        // Return the saved expense with HTTP status 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    // 2. Get all expenses (HTTP GET request to /api/expenses)
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses() {
        // Retrieve all expenses from the database
        List<Expense> expenses = expenseRepository.findAll();

        // Return the list of expenses with HTTP status 200 (OK)
        return ResponseEntity.ok(expenses);
    }

    // 3. Get a specific expense by ID (HTTP GET request to /api/expenses/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        // Find the expense by its ID
        return expenseRepository.findById(id)
                // If found, return it with HTTP status 200 (OK)
                .map(ResponseEntity::ok)
                // If not found, return HTTP status 404 (NOT FOUND)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update an existing expense (HTTP PUT request to /api/expenses/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        // Check if the expense exists by ID
        return expenseRepository.findById(id)
                .map(expense -> {
                    // Update the existing expense with new values
                    expense.setDescription(updatedExpense.getDescription());
                    expense.setAmount(updatedExpense.getAmount());
                    expense.setDate(updatedExpense.getDate());
                    expense.setCategory(updatedExpense.getCategory());

                    // Save the updated expense
                    Expense savedExpense = expenseRepository.save(expense);

                    // Return the updated expense with HTTP status 200 (OK)
                    return ResponseEntity.ok(savedExpense);
                })
                // If the expense is not found, return HTTP status 404 (NOT FOUND)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete an expense (HTTP DELETE request to /api/expenses/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        // Check if the expense exists by ID
        if (!expenseRepository.existsById(id)) {
            // If not found, return HTTP status 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        }

        // Delete the expense by ID
        expenseRepository.deleteById(id);

        // Return HTTP status 204 (NO CONTENT) to indicate successful deletion
        return ResponseEntity.noContent().build();
    }
}
