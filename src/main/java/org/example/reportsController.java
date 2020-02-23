package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML
    private BarChart<?, ?> chart_categories;
    private CategoryAxis chart_y;
    private NumberAxis chart_x;

    db_connection connectionClass = new db_connection();
    private ObservableList<reportsController> data;


    @FXML
    public void loadCategories() {
        XYChart.Series<String> set = new XYChart.Series<>();
        String sqlStationName = " select * from categories ";
        try {
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            connection = connectionClass.getConnection();
            PreparedStatement pstStn = connection.prepareStatement(sqlStationName);
            ResultSet stnRS = pstStn.executeQuery(sqlStationName);
            while (stnRS.next()) {
                set.getData().add(new XYChart.Data(stnRS.getString(1));
            }

            stnRS.close();
            pstStn.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("ERR" + ex);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
    }
}
