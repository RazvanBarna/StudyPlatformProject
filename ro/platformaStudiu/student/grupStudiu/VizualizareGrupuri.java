package ro.platformaStudiu.student.grupStudiu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;


public class VizualizareGrupuri {
    private SqlConexiune conex;

    public VizualizareGrupuri(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int userID,int studentID){
        this.conex=sql;

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        loadGrupuriStudiu(studentID,layout);
        Button inapoi=new Button("inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            new GrupStudiuPage(primaryStage,sql,nume,prenume,userID,studentID);
        });
        layout.getChildren().add(inapoi);


        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grupuri de Studiu");
        primaryStage.show();

    }

    private void loadGrupuriStudiu(int studentID, VBox layout){
        String query = "{CALL AfisareGrupuriDeStudiu(?)}";

        try(PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)){
            stmt.setInt(1,studentID);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                int idGrup = rs.getInt("ID_grup_studiu");
                String materie = rs.getString("Materie");
                Label label = new Label("ID Grup: " + idGrup + " - Materie: " + materie);
                label.setFont(new Font("Times New Roman",16));
                layout.getChildren().add(label);
            }


        } catch (Exception e) {
            e.printStackTrace();
            layout.getChildren().add(new Label("Eroare la încărcarea datelor."));
        }
    }
}
