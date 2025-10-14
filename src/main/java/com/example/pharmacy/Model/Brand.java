package com.example.pharmacy.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Brand {
    private final IntegerProperty brandId;
    private final StringProperty brandName;

    public Brand(int brandId, String brandName) {
        this.brandId = new SimpleIntegerProperty(brandId);
        this.brandName = new SimpleStringProperty(brandName);
    }

    // --- Getters for Properties (for UI binding) ---

    public IntegerProperty BrandIdProperty() {
        return brandId;
    }

    public StringProperty BrandNameProperty() {
        return brandName;
    }

    // --- Convenience Getters (for accessing values directly) ---

    public int getBrandId() {
        return brandId.get();
    }

    public String getBrandName() {
        return brandName.get();
    }
    @Override
    public String toString() {
        return "Brand{" +
                "  Id=" + getBrandId() +
                ", brandName='" + getBrandName() +
                '}';
    }
}
