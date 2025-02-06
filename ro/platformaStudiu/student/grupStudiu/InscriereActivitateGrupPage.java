package ro.platformaStudiu.student.grupStudiu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.ActivitateGrup;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InscriereActivitateGrupPage {
    private SqlConexiune conex;

    public InscriereActivitateGrupPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume, int userID, int studentId, GrupStudiu grup){
        this.conex=sql;
        int grupID=grup.getIdGrup();
        List<ActivitateGrup> listaActivitati=loadActivitatiGrup(grupID);
        ComboBox<ActivitateGrup> activitatiComboBox = new ComboBox<>();
        activitatiComboBox.getItems().addAll(listaActivitati);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        Button inapoi=new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            new ManagementGrupuri(primaryStage,sql,nume,prenume,userID,studentId);
        });

        Button inscriereButton = new Button("Înscriere la activitate");
        inscriereButton.setFont(new Font("Times New Roman", 14));
        inscriereButton.setOnAction(e -> {
            ActivitateGrup activitateSelectata = activitatiComboBox.getSelectionModel().getSelectedItem();
            if (activitateSelectata != null) {
                inscriereStudentLaActivitate(studentId, activitateSelectata, statusLabel);
            } else {
                statusLabel.setText("Te rog să selectezi o activitate!");
                statusLabel.setTextFill(Color.RED);
            }
        });



        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(statusLabel,activitatiComboBox,inscriereButton,inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grupuri de Studiu");
        primaryStage.show();
    }

    private List<ActivitateGrup> loadActivitatiGrup(int grupID) {
        String query = "{CALL VeziActivitatiGrup(?)}";
        List<ActivitateGrup> activitati = new ArrayList<>();
        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, grupID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int activitateID = rs.getInt("ID_activitate");
                int grupIDActivitate = rs.getInt("ID_grup");
                int nrCurentParticipanti = rs.getInt("Nr_curent_participanti");
                int nrMinParticipanti = rs.getInt("Nr_min_participanti");
                String ziua = rs.getString("Ziua");
                Time ora = rs.getTime("Ora");
                String descriere = rs.getString("Descriere");
                int durata = rs. getInt("Durata");
                int timpExpirare = rs.getInt("Timp_expirare");
                activitati.add(new ActivitateGrup(
                        activitateID, grupIDActivitate, nrCurentParticipanti, nrMinParticipanti, ziua, ora, descriere, durata, timpExpirare
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activitati;
    }

    private void inscriereStudentLaActivitate(int studentId, ActivitateGrup activitate, Label statusLabel) {

        String query = "{CALL InscriereActivitateGrup(?, ?)}";
        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, activitate.getId_activitate());
            stmt.executeUpdate();
            statusLabel.setText("Înscrierea a fost realizată cu succes!");
            statusLabel.setTextFill(Color.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Eroare la înscriere: "+e.getMessage());
            statusLabel.setTextFill(Color.RED);
        }
    }

}
