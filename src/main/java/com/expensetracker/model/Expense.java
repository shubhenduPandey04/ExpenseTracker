package com.expensetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// Marks this as a database table
@Entity
public class Expense {

    // Primary key (Auto-generated ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // What the expense is for
    private BigDecimal amount;  // How much was spent
    private LocalDate date;     // When the expense occurred
    private String category;    // Type of expense (e.g., Food, Transport)

    // Default constructor (required by JPA)
    public Expense() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
