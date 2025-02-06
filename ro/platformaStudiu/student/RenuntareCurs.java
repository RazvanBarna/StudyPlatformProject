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
import java.util.List;

public class RenuntareCurs {
    private SqlConexiune conex;

    public RenuntareCurs(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int student_id,int user_id){
        this.conex=sql;

        javafx.scene.control.Label headerLabel = new javafx.scene.control.Label("Renuntare Curs:");
        headerLabel.setFont(new Font("Times New Roman", 20));
        headerLabel.setTextFill(Color.BLUE);

        Button inapoi = new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new StudentPage(primaryStage, conex, nume, prenume, user_id);
        });

        ComboBox<Materie> materiiComboBox = new ComboBox<>();
        materiiComboBox.setPromptText("Selecteaza o materie");

        List<Materie> materiiList = Service.getMateriiStudent(student_id);
        materiiComboBox.getItems().addAll(materiiList);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.GREEN);

        Button stergeButton = new Button("Sterge");
        stergeButton.setOnAction(e -> {
            Materie selectedMaterie = materiiComboBox.getValue();
            if (selectedMaterie != null) {
                deleteMaterie(student_id, selectedMaterie.getIdMaterie());
                statusLabel.setText("Stergerea a fost realizata cu succes!");
                statusLabel.setTextFill(Color.GREEN);
            } else {
                statusLabel.setText("Stergerea nu a putut fi realizata !");
                statusLabel.setTextFill(Color.RED);
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(headerLabel, materiiComboBox,statusLabel,stergeButton, inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stergere Materie");
        primaryStage.show();
    }

    private void deleteMaterie(int student_id, int id_materie) {
        String procedureCall = "{CALL StudentRenuntareMaterie(?, ?)}";

        try (CallableStatement stmt = SqlConexiune.con.prepareCall(procedureCall)) {
            stmt.setInt(1, student_id);
            stmt.setInt(2, id_materie);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
