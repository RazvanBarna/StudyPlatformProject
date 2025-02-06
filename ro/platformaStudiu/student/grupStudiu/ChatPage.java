package ro.platformaStudiu.student.grupStudiu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatPage {
    private SqlConexiune conex;

    public ChatPage(Stage primaryStage, SqlConexiune sql,String nume,String prenume,int userID,int studentID, GrupStudiu grup) {
        this.conex = sql;
        int grupID=grup.getIdGrup();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        List<String> mesaje = loadMesajeGrup(grupID);

        if (mesaje.isEmpty()) {
            layout.getChildren().add(new Label("Nu sunt mesaje în acest grup."));
        } else {
            for (String mesaj : mesaje) {
                Label mesajLabel = new Label(mesaj);
                mesajLabel.setFont(new Font("Times New Roman", 14));
                layout.getChildren().add(mesajLabel);
            }
        }

        Button inapoiButton = new Button("Înapoi");
        inapoiButton.setFont(new Font("Times New Roman", 14));
        inapoiButton.setOnAction(e -> {
            primaryStage.close();
            new ManagementGrupuri(primaryStage, sql, nume, prenume, userID, studentID);
        });
        layout.getChildren().add(inapoiButton);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mesaje Grup");
        primaryStage.show();
    }

    private List<String> loadMesajeGrup(int grupID) {
        String query = "{CALL SelecteazaMesajeGrup(?)}";
        List<String> mesaje = new ArrayList<>();

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, grupID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String numeStudent = rs.getString("NumeStudent");
                String prenumeStudent = rs.getString("PrenumeStudent");
                String mesaj = rs.getString("Mesaj");

                mesaje.add(numeStudent + " " + prenumeStudent + ": " + mesaj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesaje;
    }
}