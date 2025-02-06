package ro.platformaStudiu.student.grupStudiu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;

public class TrimiteMesajPage {
    private SqlConexiune conex;

    public TrimiteMesajPage(Stage primaryStage, SqlConexiune sql,String nume,String prenume,int userID, int studentID, GrupStudiu grup) {
        this.conex = sql;
        int grupID=grup.getIdGrup();

        VBox layout = new VBox(10);

        Label mesajLabel = new Label("Scrie mesajul:");
        mesajLabel.setFont(new Font("Times New Roman", 14));

        TextArea mesajTextArea = new TextArea();
        mesajTextArea.setPromptText("Introduceți mesajul...");
        mesajTextArea.setWrapText(true);
        mesajTextArea.setMaxHeight(150);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        Button trimiteButton = new Button("Trimite");
        trimiteButton.setFont(new Font("Times New Roman", 14));
        trimiteButton.setOnAction(e -> {
            String mesaj = mesajTextArea.getText();
            if (!mesaj.isEmpty()) {
                boolean success = trimiteMesaj(studentID, mesaj, grupID);
                if (success) {

                    statusLabel.setText("Mesajul a fost trimis cu succes!");
                    statusLabel.setTextFill(Color.GREEN);
                    mesajTextArea.clear();
                } else {
                    statusLabel.setText("A apărut o problemă la trimiterea mesajului.");
                    statusLabel.setTextFill(Color.RED);
                }
            } else {
                statusLabel.setText("Mesajul nu poate fi gol!");
                statusLabel.setTextFill(Color.RED);
            }
        });

        Button inapoiButton = new Button("Înapoi");
        inapoiButton.setFont(new Font("Times New Roman", 14));
        inapoiButton.setOnAction(e -> {
            primaryStage.close();
            new ManagementGrupuri(primaryStage,sql,nume,prenume, userID, studentID);
        });

        layout.getChildren().addAll(mesajLabel, mesajTextArea, trimiteButton, statusLabel, inapoiButton);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setTitle("Trimite Mesaj");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private boolean trimiteMesaj(int studentID, String mesaj, int grupID) {
        String query = "{CALL TrimiteMesaj(?, ?, ?)}";
        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, studentID);
            stmt.setString(2, mesaj);
            stmt.setInt(3, grupID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
