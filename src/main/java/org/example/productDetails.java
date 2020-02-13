package org.example;

import javafx.beans.property.*;

public class productDetails extends ProductsController {




    private final StringProperty product_id;
    private final StringProperty product_name;
    private final StringProperty product_details;
    private final DoubleProperty product_price;
    private final IntegerProperty product_stock;
    private final StringProperty category_id;


    public String getProduct_id() {
        return product_id.get();
    }

    public StringProperty product_idProperty() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id.set(product_id);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public StringProperty product_nameProperty() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public String getProduct_details() {
        return product_details.get();
    }

    public StringProperty product_detailsProperty() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details.set(product_details);
    }

    public double getProduct_price() {
        return product_price.get();
    }

    public DoubleProperty product_priceProperty() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price.set(product_price);
    }

    public int getProduct_stock() {
        return product_stock.get();
    }

    public IntegerProperty product_stockProperty() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock.set(product_stock);
    }

    public String getCategory_id() {
        return category_id.get();
    }

    public StringProperty category_idProperty() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id.set(category_id);
    }

    public productDetails(String product_id, String product_name, String product_details, Double product_price, Integer product_stock, String category_id) {
        this.product_id = new SimpleStringProperty(product_id);
        this.product_name = new SimpleStringProperty(product_name);
        this.product_details = new SimpleStringProperty(product_details);
        this.product_price = new SimpleDoubleProperty(product_price);
        this.product_stock = new SimpleIntegerProperty(product_stock);
        this.category_id = new SimpleStringProperty(category_id);
    }


}
