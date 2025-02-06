package ro.platformaStudiu.student.grupStudiu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.Materie;
import ro.platformaStudiu.serviceClass.Service;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.List;

public class InscriereGrupStudiuPage {
    private SqlConexiune conex;

    public InscriereGrupStudiuPage(Stage primaryStage, SqlConexiune sql,String nume,String prenume,int user_id,int studentId){
        this.conex=sql;

        ComboBox<Materie> comboBoxMaterii = new ComboBox<>();
        List<Materie> materiiList = Service.getMateriiStudent(studentId);
        comboBoxMaterii.getItems().addAll(materiiList);

        Button inapoi=new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            new GrupStudiuPage(primaryStage,sql,nume,prenume,user_id,studentId);
        });

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setText("Alegeți materia.");
        statusLabel.setTextFill(Color.BLUE);

        Button inscriere=new Button("Inscriere grup");
        inscriere.setFont(new Font("Times New Roman", 14));
        inscriere.setOnAction(e->{
            Materie materie=comboBoxMaterii.getValue();
            int materie_id=materie.getIdMaterie();
            inscriereGrupStudiu(statusLabel,studentId,materie_id);

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(comboBoxMaterii, statusLabel,inscriere,inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Selecție Grup Studiu");
        primaryStage.show();

    }

    public void inscriereGrupStudiu(Label statusLabel, int studentID, int materieID) {
        String query = "{CALL InscriereGrupStudiu(?, ?)}";

        try (CallableStatement stmt = SqlConexiune.con.prepareCall(query)) {
            stmt.setInt(1, studentID);
            stmt.setInt(2, materieID);

            stmt.execute();

            statusLabel.setText("Inscrierea la activitate a avut loc cu succes");
            statusLabel.setTextFill(Color.GREEN);

        } catch (SQLException e) {
            statusLabel.setText("Eroare : esti deja in acest grup de studiu inscris");
            statusLabel.setTextFill(Color.RED);
            e.printStackTrace();
        }
    }
}



