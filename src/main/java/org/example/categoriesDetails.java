package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class categoriesDetails extends CategoriesController {


    private final StringProperty catID;
    private final StringProperty catName;

    public categoriesDetails(String catID, String catName) {
        this.catID = new SimpleStringProperty(catID);
        this.catName = new SimpleStringProperty(catName);
    }


    //Getters
    public String getCatID(){return catID.get();}
    public String getCatName(){return catName.get();}
    //Setters
    public void setCatID(String value){
        catID.set(value);
    }
    public void setCatName(String value){
        catName.set(value);
    }

    public StringProperty idProperty(){return catID;}
    public StringProperty nameProperty(){return catName;}


}
