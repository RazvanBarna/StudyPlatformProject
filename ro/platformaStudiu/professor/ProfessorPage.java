package ro.platformaStudiu.professor;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfessorPage {
     private SqlConexiune conex;

     public ProfessorPage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int rol_id,int user_id) {
         this.conex = sql;

         GridPane grid = new GridPane();
         grid.setPadding(new Insets(20));
         grid.setHgap(10);
         grid.setVgap(10); //spatiu intre coloane

         Label welcomeLabel = new Label("Bine ai venit "+ prenume+" " + nume+" !");
         welcomeLabel.setFont(new Font("Times New Roman", 20));
         welcomeLabel.setTextFill(Color.BLUE);

         Button vizualizareDateButon = new Button("Vizualizare Date ");
         Button catalog = new Button("Vizualizare catalog ");
         Button deautentificareButton = new Button("Deautentificare ");
         Button impartire = new Button("Impartirea procentuala a activitatilor");
         Button programare = new Button("Programare activitati");



         vizualizareDateButon.setFont(new Font("Times New Roman", 16));
         catalog.setFont(new Font("Times New Roman", 16));
         deautentificareButton.setFont(new Font("Times New Roman", 16));
         programare.setFont(new Font("Times New Roman", 16));
         impartire.setFont(new Font("Times New Roman", 16));
         deautentificareButton.setFont(new Font("Times New Roman", 16));

         grid.add(welcomeLabel, 0, 0, 2, 1);
         grid.add(vizualizareDateButon, 0, 1);
         grid.add(catalog, 0, 2);
         grid.add(programare, 0, 3);
         grid.add(impartire, 0, 4);
         grid.add(deautentificareButton, 0, 5);

         deautentificareButton.setOnAction(e -> {
             primaryStage.close();
             new LoginPage(primaryStage, conex);
         });

         vizualizareDateButon.setOnAction(e -> {
             primaryStage.close();
             new VizualizareDate(primaryStage,conex,nume,prenume,rol_id,user_id);
         });

         programare.setOnAction(e ->{
             primaryStage.close();
             int profesorID=getProfessorId(user_id);
             new ProgramareActivitatiProfesorPage(primaryStage,conex,nume,prenume,user_id,profesorID);
         });
         impartire.setOnAction(e->{
             primaryStage.close();
             int profesorID=getProfessorId(user_id);
             new SetareProcentProfPage(primaryStage,conex,nume,prenume,user_id,profesorID);
         });
         catalog.setOnAction(e->{
             primaryStage.close();
             int profesorID=getProfessorId(user_id);
             new CatalogProfPage(primaryStage,conex,nume,prenume,user_id,profesorID);
         });



         Scene scene = new Scene(grid, 800, 500);
         primaryStage.setTitle("Professor Page");
         primaryStage.setScene(scene);
         primaryStage.show();
     }

    private   int getProfessorId(int userId) {
        String query = "SELECT profesor_id FROM profesori WHERE profesor_id_user = ?";
        try (
                PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("profesor_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
