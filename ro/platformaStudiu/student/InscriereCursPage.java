package ro.platformaStudiu.student;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.Materie;
import ro.platformaStudiu.serviceClass.Service;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class InscriereCursPage {
    private SqlConexiune conex;
    private int studentId;

    public InscriereCursPage(Stage primaryStage, SqlConexiune sql,String nume,String prenume,int user_id, int student_id) {
        this.conex = sql;
        this.studentId=student_id;


        // Label pentru interfață
        Label titleLabel = new Label("Alege o materie pentru înscriere:");
        titleLabel.setTextFill(Color.BLUE);
        titleLabel.setFont(new Font("Times New Roman", 18));

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.GREEN);

        // ComboBox pentru a selecta o materie
        ComboBox<Materie> materiiComboBox = new ComboBox<>();
        materiiComboBox.setPromptText("Selecteaza o materie");


        List<Materie> materiiList = Service.getMaterii();
        materiiComboBox.getItems().addAll(materiiList);

        Button inscriereButton = new Button("Inscrie-te");
        inscriereButton.setFont(new Font("Times New Roman", 14));
        inscriereButton.setOnAction(e -> {
            Materie selectedMaterie = materiiComboBox.getValue();
            if (selectedMaterie != null) {
                if (inscriereLaMaterie(selectedMaterie.getIdMaterie())) {
                    statusLabel.setText("Inscriere realizata cu succes!");
                    statusLabel.setTextFill(Color.GREEN);
                } else {
                    statusLabel.setText("Eroare la inscriere.Esti deja inscris!");
                    statusLabel.setTextFill(Color.RED);
                }
            } else {
                statusLabel.setText("Selecteaza o materie!");
                statusLabel.setTextFill(Color.RED);
            }
        });

        Button inapoi = new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new StudentPage(primaryStage, conex, nume, prenume, user_id);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, materiiComboBox, inscriereButton, statusLabel, inapoi);

        // Creează scena
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscriere Materie");
        primaryStage.show();

    }

    private boolean inscriereLaMaterie(int materieId) {
        String query = "{CALL StudentInscriereMaterie(?, ?)}";

        try (
             PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, materieId);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
