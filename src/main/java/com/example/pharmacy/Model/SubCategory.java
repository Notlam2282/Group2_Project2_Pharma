package com.example.pharmacy.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class SubCategory {

    private final IntegerProperty subCategoryId;
    private final StringProperty subCategoryName;
    private final IntegerProperty categoryId;

    /**
     * Constructs a new SubCategory with the given values.
     * Properties are initialized with Simple*Property wrappers for binding.
     */
    public SubCategory(int subCategoryId, String subCategoryName, int categoryId) {
        this.subCategoryId = new SimpleIntegerProperty(subCategoryId);
        this.subCategoryName = new SimpleStringProperty(subCategoryName);
        this.categoryId = new SimpleIntegerProperty(categoryId);
    }

    // --- Getters for Properties (for UI binding) ---

    public IntegerProperty subCategoryIdProperty() {
        return subCategoryId;
    }

    public StringProperty subCategoryNameProperty() {
        return subCategoryName;
    }

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    // --- Convenience Getters (for accessing values directly) ---

    public int getSubCategoryId() {
        return subCategoryId.get();
    }

    public String getSubCategoryName() {
        return subCategoryName.get();
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId=" + getSubCategoryId() +
                ", subCategoryName='" + getSubCategoryName() + '\'' +
                ", categoryId=" + getCategoryId() +
                '}';
    }
}
