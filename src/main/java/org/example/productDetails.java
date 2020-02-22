package org.example;

import javafx.util.converter.DateTimeStringConverter;

import java.sql.Date;

public class productDetails extends ProductsController {


    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_details() {
        return product_details;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public Integer getProduct_stock() {
        return product_stock;
    }

    public String getCategory_id() {
        return category_id;
    }

    public Date getReceived_date() {
        return received_date;
    }

    public String getStatus() {
        return status;
    }

    public productDetails(String product_id, String product_name, String product_details, double product_price, int product_stock, String category_id, Date received_date, String status) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_details = product_details;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.category_id = category_id;
        this.received_date = received_date;
        this.status = status;
    }

    private final String product_id;
    private final String product_name;
    private final String product_details;
    private final Double product_price;
    private final Integer product_stock;
    private final String category_id;
    private final Date received_date;
    private final String status;





}
