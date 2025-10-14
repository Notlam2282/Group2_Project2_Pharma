package com.example.pharmacy.Model;

import javafx.beans.property.*;
import java.util.Date;

public class Product {
    private final IntegerProperty medicineId;
    private final IntegerProperty brandId;
    private final StringProperty brandName; // New property for the brand name
    private final StringProperty productName;
    private final StringProperty type;
    private final StringProperty status;
    private final DoubleProperty price;
    private final ObjectProperty<Date> date;
    private final StringProperty image;
    private final IntegerProperty categoryId;
    private final StringProperty categoryName; // New property for the category name
    private final IntegerProperty subCategoryId;
    private final StringProperty subCategoryName; // New property for the sub-category name


    // Constructor updated to include names
    public Product(int medicineId, int brandId, String brandName, String productName, String type, String status, double price, Date date, String image, int categoryId, String categoryName, int subCategoryId, String subCategoryName) {
        this.medicineId = new SimpleIntegerProperty(medicineId);
        this.brandId = new SimpleIntegerProperty(brandId);
        this.brandName = new SimpleStringProperty(brandName);
        this.productName = new SimpleStringProperty(productName);
        this.type = new SimpleStringProperty(type);
        this.status = new SimpleStringProperty(status);
        this.price = new SimpleDoubleProperty(price);
        this.date = new SimpleObjectProperty<>(date);
        this.image = new SimpleStringProperty(image);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.categoryName = new SimpleStringProperty(categoryName);
        this.subCategoryId = new SimpleIntegerProperty(subCategoryId);
        this.subCategoryName = new SimpleStringProperty(subCategoryName);
    }

    // Getters for properties
    public IntegerProperty medicineIdProperty() { return medicineId; }
    public IntegerProperty brandIdProperty() { return brandId; }
    public StringProperty brandNameProperty() { return brandName; } // Getter for brandName
    public StringProperty productNameProperty() { return productName; }
    public StringProperty typeProperty() { return type; }
    public StringProperty statusProperty() { return status; }
    public DoubleProperty priceProperty() { return price; }
    public ObjectProperty<Date> dateProperty() { return date; }
    public StringProperty imageProperty() { return image; }
    public IntegerProperty categoryIdProperty() { return categoryId; }
    public StringProperty categoryNameProperty() { return categoryName; } // Getter for categoryName
    public IntegerProperty subCategoryIdProperty() { return subCategoryId; }
    public StringProperty subCategoryNameProperty() { return subCategoryName; } // Getter for subCategoryName


    // Getter methods for accessing values
    public int getMedicineId() { return medicineId.get(); }
    public int getBrandId() { return brandId.get(); }
    public String getBrandName() { return brandName.get(); }
    public String getProductName() { return productName.get(); }
    public String getType() { return type.get(); }
    public String getStatus() { return status.get(); }
    public double getPrice() { return price.get(); }
    public Date getDate() { return date.get(); }
    public String getImage() { return image.get(); }
    public int getCategoryId() { return categoryId.get(); }
    public String getCategoryName() { return categoryName.get(); }
    public int getSubCategoryId() { return subCategoryId.get(); }
    public String getSubCategoryName() { return subCategoryName.get(); }


    @Override
    public String toString() {
        return "Product{" +
                "medicineId=" + getMedicineId() +
                ", brandId=" + getBrandId() +
                ", brandName='" + getBrandName() + '\'' +
                ", productName='" + getProductName() + '\'' +
                ", type='" + getType() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", price=" + getPrice() +
                ", date=" + getDate() +
                ", image='" + getImage() + '\'' +
                ", categoryId=" + getCategoryId() +
                ", categoryName='" + getCategoryName() + '\'' +
                ", subCategoryId=" + getSubCategoryId() +
                ", subCategoryName='" + getSubCategoryName() + '\'' +
                '}';
    }
}
