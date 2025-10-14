package com.example.pharmacy;

import com.example.pharmacy.Model.Brand;
import com.example.pharmacy.Model.Category;
import com.example.pharmacy.Model.Product;
import com.example.pharmacy.Model.SubCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;


import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class PharmacyController {

    @FXML
    private Label welcomeText;
    @FXML
    private Label statusLabel;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> medicineIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> brandColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, String> subCategoryColumn;
    @FXML
    private MenuButton categoryFilterMenu;
    @FXML
    private MenuButton subCategoryFilterMenu;
    @FXML
    private MenuButton brandFilterMenu;

    // API URLs for each data type
    private static final String PRODUCTS_API_URL = "http://localhost:8080/api/customer-operationsproducts";
    private static final String CATEGORIES_API_URL = "http://localhost:8080/api/customer-operationscategories";
    private static final String SUB_CATEGORIES_API_URL = "http://localhost:8080/api/customer-operationssubcategories";
    private static final String BRANDS_API_URL = "http://localhost:8080/api/customer-operationsbrands";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    // ObservableLists to hold the fetched data for the UI
    private ObservableList<Product> productsData;
    private ObservableList<Product> allProducts;
    private List<Category> categoriesData;
    private List<SubCategory> subCategoriesData;
    private List<Brand> brandsData;

    // Maps for quick lookup of category, subcategory, and brand names by ID
    private Map<Integer, String> categoryMap;
    private Map<Integer, String> subCategoryMap;
    private Map<Integer, String> brandMap;

    @FXML
    public void initialize() {
        // Set up the columns to bind to the Product properties
        medicineIdColumn.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Use the new name-based properties from the Product class
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        subCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("subCategoryName"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brandName"));
    }

    @FXML
    protected void fetchDataAndPopulate() {
        Platform.runLater(() -> statusLabel.setText("Fetching all data from the API..."));

        // Use CompletableFuture to make API calls concurrently
        CompletableFuture<List<Product>> productsFuture = fetchApiData(PRODUCTS_API_URL, new TypeToken<List<Product>>(){}.getType());
        CompletableFuture<List<Category>> categoriesFuture = fetchApiData(CATEGORIES_API_URL, new TypeToken<List<Category>>(){}.getType());
        CompletableFuture<List<SubCategory>> subCategoriesFuture = fetchApiData(SUB_CATEGORIES_API_URL, new TypeToken<List<SubCategory>>(){}.getType());
        CompletableFuture<List<Brand>> brandsFuture = fetchApiData(BRANDS_API_URL, new TypeToken<List<Brand>>(){}.getType());

        // Wait for all futures to complete
        CompletableFuture.allOf(productsFuture, categoriesFuture, subCategoriesFuture, brandsFuture)
                .thenRun(() -> {
                    try {
                        List<Product> fetchedProducts = productsFuture.get();
                        this.categoriesData = categoriesFuture.get();
                        this.subCategoriesData = subCategoriesFuture.get();
                        this.brandsData = brandsFuture.get();

                        // Create lookup maps for category, subcategory, and brand names
                        categoryMap = categoriesData.stream()
                                .collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));

                        subCategoryMap = subCategoriesData.stream()
                                .collect(Collectors.toMap(SubCategory::getSubCategoryId, SubCategory::getSubCategoryName));

                        brandMap = brandsData.stream()
                                .collect(Collectors.toMap(Brand::getBrandId, Brand::getBrandName));

                        // Create a new list of Products with the names populated
                        List<Product> populatedProducts = fetchedProducts.stream()
                                .map(p -> new Product(
                                        p.getMedicineId(),
                                        p.getBrandId(),
                                        brandMap.getOrDefault(p.getBrandId(), "N/A"),
                                        p.getProductName(),
                                        p.getType(),
                                        p.getStatus(),
                                        p.getPrice(),
                                        p.getDate(),
                                        p.getImage(),
                                        p.getCategoryId(),
                                        categoryMap.getOrDefault(p.getCategoryId(), "N/A"),
                                        p.getSubCategoryId(),
                                        subCategoryMap.getOrDefault(p.getSubCategoryId(), "N/A")
                                ))
                                .collect(Collectors.toList());

                        this.allProducts = FXCollections.observableArrayList(populatedProducts);

                        // Populate the MenuButtons with the fetched data
                        populateFilterMenus();

                        // Update the UI after all data has been fetched
                        Platform.runLater(() -> {
                            productsData = FXCollections.observableArrayList(allProducts);
                            statusLabel.setText("Successfully fetched all data. Total products: " + allProducts.size());
                            productTableView.setItems(productsData);
                        });

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> statusLabel.setText("Error processing API responses."));
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    Platform.runLater(() -> statusLabel.setText("Error connecting to one or more APIs."));
                    return null;
                });
    }

    private void populateFilterMenus() {
        // Populate Category Menu
        categoryFilterMenu.getItems().clear();
        MenuItem allCategoriesItem = new MenuItem("All Categories");
        allCategoriesItem.setOnAction(this::handleCategoryFilter);
        categoryFilterMenu.getItems().add(allCategoriesItem);
        for (Category cat : categoriesData) {
            MenuItem item = new MenuItem(cat.getCategoryName());
            item.setOnAction(e -> handleCategoryFilter(e));
            categoryFilterMenu.getItems().add(item);
        }

        // Populate SubCategory Menu
        subCategoryFilterMenu.getItems().clear();
        MenuItem allSubCategoriesItem = new MenuItem("All SubCategories");
        allSubCategoriesItem.setOnAction(this::handleSubCategoryFilter);
        subCategoryFilterMenu.getItems().add(allSubCategoriesItem);
        for (SubCategory subCat : subCategoriesData) {
            MenuItem item = new MenuItem(subCat.getSubCategoryName());
            item.setOnAction(e -> handleSubCategoryFilter(e));
            subCategoryFilterMenu.getItems().add(item);
        }

        // Populate Brand Menu
        brandFilterMenu.getItems().clear();
        MenuItem allBrandsItem = new MenuItem("All Brands");
        allBrandsItem.setOnAction(this::handleBrandFilter);
        brandFilterMenu.getItems().add(allBrandsItem);
        for (Brand brand : brandsData) {
            MenuItem item = new MenuItem(brand.getBrandName());
            item.setOnAction(e -> handleBrandFilter(e));
            brandFilterMenu.getItems().add(item);
        }
    }

    @FXML
    protected void handleCategoryFilter(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        if (selectedItem.getText().equals("All Categories")) {
            resetFilter();
        } else {
            // Find the ID for the selected category
            int selectedCategoryId = categoriesData.stream()
                    .filter(c -> c.getCategoryName().equals(selectedItem.getText()))
                    .findFirst()
                    .map(Category::getCategoryId)
                    .orElse(-1);

            // Filter the products
            productsData.setAll(allProducts.stream()
                    .filter(p -> p.getCategoryId() == selectedCategoryId)
                    .collect(Collectors.toList()));
        }
    }

    @FXML
    protected void handleSubCategoryFilter(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        if (selectedItem.getText().equals("All SubCategories")) {
            resetFilter();
        } else {
            // Find the ID for the selected subcategory
            int selectedSubCategoryId = subCategoriesData.stream()
                    .filter(s -> s.getSubCategoryName().equals(selectedItem.getText()))
                    .findFirst()
                    .map(SubCategory::getSubCategoryId)
                    .orElse(-1);

            // Filter the products
            productsData.setAll(allProducts.stream()
                    .filter(p -> p.getSubCategoryId() == selectedSubCategoryId)
                    .collect(Collectors.toList()));
        }
    }

    @FXML
    protected void handleBrandFilter(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        if (selectedItem.getText().equals("All Brands")) {
            resetFilter();
        } else {
            // Find the ID for the selected brand
            int selectedBrandId = brandsData.stream()
                    .filter(b -> b.getBrandName().equals(selectedItem.getText()))
                    .findFirst()
                    .map(Brand::getBrandId)
                    .orElse(-1);

            // Filter the products
            productsData.setAll(allProducts.stream()
                    .filter(p -> p.getBrandId() == selectedBrandId)
                    .collect(Collectors.toList()));
        }
    }

    private void resetFilter() {
        if (productsData != null) {
            productsData.setAll(allProducts);
        }
    }

    /**
     * Generic method to fetch data from an API and parse the JSON response.
     * @param apiUrl The URL of the API endpoint.
     * @param type The Gson TypeToken for the expected response type.
     * @param <T> The type of the data to be returned.
     * @return A CompletableFuture with the list of data.
     */
    private <T> CompletableFuture<List<T>> fetchApiData(String apiUrl, Type type) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> (List<T>) gson.fromJson(responseBody, type))
                .exceptionally(ex -> {
                    System.err.println("API request to " + apiUrl + " failed: " + ex.getMessage());
                    return null;
                });
    }
}
