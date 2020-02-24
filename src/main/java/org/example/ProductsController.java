package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    public TextField txt_productID;
    public TextField txt_productName;
    public TextField txt_productPrice;
    public Label lbl_messageAddProduct;
    public Button btn_addProduct;
    public Button btn_printQR;
    public Button btn_deleteProduct;
    public ImageView img_qr;
    public Button btnlogout;
    public TextArea txt_productDetails;
    public TextArea txt_printers;
    public ComboBox comboBox_Categories;
    public ComboBox comboBox_Status;
    public ComboBox comboBox_Products;
    //public ComboBox<Rectangle> comboBox_Status = new ComboBox<Rectangle>();
    public TableView<ProductsController> tbl_prodTable;
    public TableColumn<ProductsController, String> column_prodID;
    public TableColumn<ProductsController, String> column_prodName;
    public TableColumn<ProductsController, String> column_description;
    public TableColumn<ProductsController, String> column_prodPrice;
    public TableColumn<ProductsController, String> column_prodStock;
    public TableColumn<ProductsController, String> column_prodCategory;
    public TableColumn<ProductsController, String> column_dateReceived;
    public TableColumn<ProductsController, String> column_status;
    private ObservableList<ProductsController> data;




    db_connection connectionClass = new db_connection();

   // comboBox_Status = new ComboBox<>();

    @FXML
    private void addProduct() throws IOException {
        String sql = "INSERT INTO products(product_id, product_name, product_price, product_details, catID, status)" + " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            // create the mysql insert preparedstatement
            Connection connection = connectionClass.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, txt_productID.getText());
            preparedStmt.setString(2, txt_productName.getText());
            preparedStmt.setDouble(3, Double.parseDouble(txt_productPrice.getText()));
            preparedStmt.setString(4, txt_productDetails.getText());
            preparedStmt.setString(5, handleCategory());
            preparedStmt.setString(6, handleStatus());
            preparedStmt.execute();
            System.out.println("Product added to the database!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The product " + txt_productName.getText() + " was added succesfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txt_productID.clear();
        txt_productName.clear();
        txt_productPrice.clear();
        txt_productDetails.clear();
        loadProdFromDatabase();

    }

    @FXML
    private void deleteProduct() {
        productDetails selectedIndex = (productDetails)tbl_prodTable.getSelectionModel().getSelectedItem();
        String selectedItem = selectedIndex.getProduct_id();
        System.out.println("Selected item to delete is " + selectedItem + " " + selectedIndex.getProduct_name());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete product " + selectedIndex.getProduct_name() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Connection connection = connectionClass.getConnection();
            PreparedStatement preparedStmt = null;
            try {
                String sql = "DELETE FROM products WHERE product_id = ?";
                preparedStmt = connection.prepareStatement(sql);
                preparedStmt.setString(1, selectedItem);
                preparedStmt.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tbl_prodTable.getItems().remove(selectedIndex);
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }

    }

    @FXML
    private void loadProdFromDatabase() throws IOException {

        try {
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM products");
            while (rs.next()) {
                data.add(new productDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getDate(7), rs.getString(8)));
            }
            column_prodID.setCellValueFactory(new PropertyValueFactory<>("product_id")); //1
            column_prodName.setCellValueFactory(new PropertyValueFactory<>("product_name")); //2
            column_description.setCellValueFactory(new PropertyValueFactory<>("product_details")); //3
            column_prodPrice.setCellValueFactory(new PropertyValueFactory<>("product_price")); //4
            column_prodStock.setCellValueFactory(new PropertyValueFactory<>("product_stock")); //5
            column_prodCategory.setCellValueFactory(new PropertyValueFactory<>("category_id")); //6
            column_dateReceived.setCellValueFactory(new PropertyValueFactory<>("received_date")); //7
            column_status.setCellValueFactory(new PropertyValueFactory<>("status")); //8
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }


        tbl_prodTable.setItems(null);
        tbl_prodTable.setItems(data);
        System.out.println("Products loaded to table");
    }


    @FXML
    public void loadProducts() {
        String sqlStationName = " select * from products ";
        try {
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            connection = connectionClass.getConnection();
            PreparedStatement pstStn = connection.prepareStatement(sqlStationName);
            ResultSet stnRS = pstStn.executeQuery(sqlStationName);
            while (stnRS.next()) {
                comboBox_Products.getItems().add(stnRS.getString("product_name"));
            }

            stnRS.close();
            pstStn.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("ERR" + ex);
        }
    }





    @FXML
    public void loadCategories() {
        String sqlStationName = " select * from categories ";
        try {
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            connection = connectionClass.getConnection();
            PreparedStatement pstStn = connection.prepareStatement(sqlStationName);
            ResultSet stnRS = pstStn.executeQuery(sqlStationName);
            while (stnRS.next()) {
                comboBox_Categories.getItems().add(stnRS.getString("catID"));
            }
            stnRS.close();
            pstStn.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("ERR" + ex);
        }
    }

    @FXML
    public String handleProducts(){

        String product = String.valueOf(comboBox_Products.getValue());
        System.out.println(product);
        return product;

    }

    @FXML
    public String handleCategory() {
        String category = String.valueOf(comboBox_Categories.getValue());
        System.out.println(category);
        return category;
    }


    @FXML
    public void loadStatus(){
        String loadstatus = String.valueOf(comboBox_Status.getItems().addAll("New", "Used"));
        System.out.println("Product status combobox loaded!");

    }


    @FXML
    public String handleStatus(){

        String status = String.valueOf(comboBox_Status.getValue());
        System.out.println(status);
        return status;
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadProdFromDatabase();
            loadCategories();
            loadProducts();
            loadStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void getPrinters(){

        ObservableSet<Printer> printers = Printer.getAllPrinters();
        for(Printer printer: printers){

            txt_printers.appendText(printer.getName() + "\n");

        }

    }






    @FXML
    public generateQRCode(String x){
        try {
            String qrCodeText = "SELECT * FROM categories WHERE product_id=" + x;
            String filePath = "src/main/resources/org/QRCodes/" + x + ".png";

            int size = 400;
            String fileType = "png";
            File qrFile = new File(filePath);
            // Create the ByteMatrix for the QR-Code that encodes the given String
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            // Make the BufferedImage that are to hold the QRCode
            int matrixWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);
            // Paint and save the image using the ByteMatrix
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, qrFile);
            javafx.scene.image.Image qrImage = new Image(new FileInputStream(filePath));
            img_qr.setImage(qrImage);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}







