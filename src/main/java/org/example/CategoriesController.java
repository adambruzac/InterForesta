package org.example;

import Connection.db_connection;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class CategoriesController {


    @FXML
    public TableView<CategoriesController> tbl_catTable;

    @FXML
    public TableColumn<categoriesDetails, String> column_catID;

    @FXML
    public TableColumn<categoriesDetails, String> column_catName;

    @FXML
    public TextField txt_categoyID;

    @FXML
    public TextField txt_categoryName;

    @FXML
    public Label lbl_importFile;

    @FXML
    public ImageView img_qr;

    private ObservableList<CategoriesController> data;



    db_connection connectionClass=new db_connection();
    Connection connection=connectionClass.getConnection();

    @FXML
    private void addCategory() throws IOException {


    String sql = "INSERT INTO categories(catID, category_name)" + " VALUES (?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, txt_categoyID.getText());
            preparedStmt.setString (2, txt_categoryName.getText());
            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("Category added to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadDataFromDatabase();
        generateQRCode();

    }

    /*-----------------------------------------------------------------*/
    @FXML
    private void loadDataFromDatabase() throws IOException{
    try {
        data = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM categories");
        while (rs.next()){

            data.add(new categoriesDetails(rs.getString(1), rs.getString(2)));

        }

    } catch (SQLException ex){
        System.out.println("Error" + ex);
    }
    column_catID.setCellValueFactory(new PropertyValueFactory<>("catID"));
    column_catName.setCellValueFactory(new PropertyValueFactory<>("catName"));

    tbl_catTable.setItems(null);
    tbl_catTable.setItems(data);

    System.out.println("Button to view categories pressed!");

    }

    /*-----------------------------------------------------------------*/
    @FXML
    public void generateQRCode(){
        try {
            String qrCodeText = "SELECT * FROM categories WHERE catID=" + txt_categoyID.getText();
            String filePath = txt_categoyID.getText() + ".png";

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
            Image qrImage = new Image(new FileInputStream(filePath));
            img_qr.setImage(qrImage);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    public void printQRCode(){


    }


}