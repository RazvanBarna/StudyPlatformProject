package ro.platformaStudiu.student;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.LoginPage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.VizualizareDate;
import ro.platformaStudiu.student.catalog.CatalogPage;
import ro.platformaStudiu.student.grupStudiu.GrupStudiuPage;

import java.sql.*;


public class StudentPage {
    private SqlConexiune conex;

    public StudentPage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int user_id){
        this.conex=sql;

        GridPane grid=new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10); //spatiu intre coloane



        Label welcomeLabel = new Label("Bine ai venit "+ prenume+" " + nume+" !");
        welcomeLabel.setFont(new Font("Times New Roman", 20));
        welcomeLabel.setTextFill(Color.BLUE);

        Button activitati= new Button(" Vezi activitatile de azi ");
        Button vizualizareDateButon= new Button("Vizualizare Date ");
        Button inscriereCursuriButon= new Button("Inscriere cursuri ");
        Button inscriereActivitatiButon= new Button("Inscriere activitati ");
        Button deautentificareButton= new Button("Deautentificare ");
        Button catalog= new Button("Vezi note ");
        Button managementCurs = new Button("Renuntare cursuri ");
        Button grupuriDeStudiu=new Button("Grupuri de studiu");

        vizualizareDateButon.setFont(new Font("Times New Roman", 16));
        deautentificareButton.setFont(new Font("Times New Roman", 16));
        inscriereCursuriButon.setFont(new Font("Times New Roman", 16));
        catalog.setFont(new Font("Times New Roman", 16));
        managementCurs.setFont(new Font("Times New Roman", 16));
        activitati.setFont(new Font("Times New Roman", 16));
        inscriereActivitatiButon.setFont(new Font("Times new Roman",16));
        grupuriDeStudiu.setFont(new Font("Times new Roman",16));

        grid.add(activitati,0,2);
        grid.add(welcomeLabel, 0, 0, 2, 1);
        grid.add(vizualizareDateButon, 0, 3);
        grid.add(inscriereCursuriButon, 0, 4);
        grid.add(deautentificareButton, 0, 9);
        grid.add(inscriereActivitatiButon,0,5);
        grid.add(catalog,0,8);
        grid.add(managementCurs,0,7);
        grid.add(grupuriDeStudiu,0,6);

        deautentificareButton.setOnAction(e ->{
            primaryStage.close();
            new LoginPage(primaryStage,conex);
        });
        vizualizareDateButon.setOnAction(e->{
            primaryStage.close();
            new VizualizareDate(primaryStage,conex,nume,prenume,3,user_id);
        });
        catalog.setOnAction( e->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new CatalogPage(primaryStage,sql,nume,prenume,user_id,student_id);
        });
        inscriereCursuriButon.setOnAction(e->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new InscriereCursPage(primaryStage,sql,nume,prenume,user_id,student_id);
        });
        activitati.setOnAction(e ->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new TodayActivitiesPage(primaryStage,sql,nume,prenume,user_id,student_id);
        });
        managementCurs.setOnAction(e->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new RenuntareCurs(primaryStage,sql,nume,prenume,student_id,user_id);
        });
        inscriereActivitatiButon.setOnAction(e->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new ActivitatiSelectStudentPage(primaryStage,sql,nume,prenume,user_id,student_id);
        });
        grupuriDeStudiu.setOnAction(e->{
            primaryStage.close();
            int student_id=getStudentId(user_id);
            new GrupStudiuPage(primaryStage,sql,nume,prenume,user_id,student_id);
        });




        Scene scene = new Scene(grid, 800, 500);
        primaryStage.setTitle("Student Page");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private  int getStudentId(int userId) {
        String query = "SELECT student_id FROM studenti WHERE student_id_user = ?";
        try (
             PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("student_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
