package org.example;

import java.io.IOException;

public class dashboardController {


public void showProducts(){

    try {
        App.setRoot("addProduct");
    } catch (IOException e) {
        e.printStackTrace();
    }


}


    public void showCategories(){


        try {
            App.setRoot("addCategory");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
