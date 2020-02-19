package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    public TextField txt_productID;
    public TextField txt_productName;
    public TextField txt_productPrice;
    public Button btn_addProduct;
    public Button btn_printQR;
    public Button btn_deleteProduct;
    public ImageView img_qr;
    public Button btnlogout;
    public TextArea txt_productDetails;
    public ComboBox comboBox_Categories;
    public TableView<ProductsController> tbl_prodTable;
    public TableColumn<ProductsController, String> column_prodID;
    public TableColumn<ProductsController, String> column_prodName;
    public TableColumn<ProductsController, String> column_description;
    db_connection connectionClass = new db_connection();
    private ObservableList<ProductsController> data;

    @FXML
    private void addProduct() {
        String sql = "INSERT INTO products(product_id, product_name, product_price, product_details, catID)" + " VALUES (?, ?, ?, ?, ?)";
        try {
            // create the mysql insert preparedstatement
            Connection connection = connectionClass.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, txt_productID.getText());
            preparedStmt.setString(2, txt_productName.getText());
            preparedStmt.setDouble(3, Double.parseDouble(txt_productPrice.getText()));
            preparedStmt.setString(4, txt_productDetails.getText());
            preparedStmt.setString(5, handleCategory());
            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("Product added to the database!");
            loadProdFromDatabase();
            generateQRCode();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txt_productID.clear();
        txt_productName.clear();
        txt_productPrice.clear();
        txt_productDetails.clear();
    }

    @FXML
    private void deleteProduct(){
       // int selectedIndex = tbl_prodTable.getSelectionModel().getSelectedIndex(data);
       // String selectedItem = column_prodID.getText(selectedIndex);
       // System.out.println(selectedItem);


       //*String sql = "DELETE FROM products WHERE product_id = ?";
        /*if(selectedIndex >= 0) {
          //  Connection connection = connectionClass.getConnection();
            //PreparedStatement preparedStmt = null;
            try {
              //  preparedStmt = connection.prepareStatement(sql);
               // preparedStmt.setString(1, selectedItem);
                //preparedStmt.execute();
                System.out.println(selectedItem);
            } catch (Exception e) {
                e.printStackTrace();
            }*/



           // tbl_prodTable.getItems().remove(selectedIndex);
        }

    @FXML
    private void loadProdFromDatabase() throws IOException {
        try {
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM products");
            while (rs.next()) {
                data.add(new productDetails(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
        column_prodID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        column_prodName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("product_details"));
        tbl_prodTable.setItems(null);
        tbl_prodTable.setItems(data);
        System.out.println("Products loaded to table");
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
    public String handleCategory() {
        String category = String.valueOf(comboBox_Categories.getValue());
        System.out.println(category);
        return category;
    }
    @FXML
    public void goBack() {
        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit(){
        Platform.exit();
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadProdFromDatabase();
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void generateQRCode(){
        try {
            String qrCodeText = "SELECT * FROM categories WHERE product_id=" + txt_productID.getText();
            String filePath = txt_productID.getText() + ".png";

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







