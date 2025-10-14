package com.example.pharmacy.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {
    private final IntegerProperty categoryId;
    private final StringProperty categoryName;

    /**
     * Constructs a new Category with the given values.
     * Properties are initialized with Simple*Property wrappers for binding.
     */
    public Category(int categoryId, String categoryName) {
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.categoryName = new SimpleStringProperty(categoryName);
    }

    // --- Getters for Properties (for UI binding) ---

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public StringProperty categoryNameProperty() {
        return categoryName;
    }

    // --- Convenience Getters (for accessing values directly) ---

    public int getCategoryId() {
        return categoryId.get();
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + getCategoryId() +
                ", categoryName='" + getCategoryName() + '\'' +
                '}';
    }
}