package ro.platformaStudiu.student.grupStudiu;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.student.Student;

import java.sql.*;


public class TableStudentiPage {
    private SqlConexiune conex;
    private ObservableList<Student> studentData;
    private TableView<Student> table;

    public TableStudentiPage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int userID,int studentID,GrupStudiu grup){
        this.conex=sql;

        int idGrup=grup.getIdGrup();

        Label headerLabel = new Label("Studentii din grup:");
        headerLabel.setFont(new Font("Times New Roman", 20));
        headerLabel.setTextFill(Color.BLUE);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        Button inapoi=new Button("inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            new ManagementGrupuri(primaryStage,sql,nume,prenume,userID,studentID);
        });

        table = initializeTableViewGrup();
        studentData = getStudentGrup(idGrup);
        table.setItems(studentData);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(headerLabel,statusLabel,table,inapoi);

        // Creează scena
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setTitle("Tabel Studenți");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private TableView<Student> initializeTableViewGrup() {
        TableView<Student> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Student, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume()));
        colNume.setPrefWidth(200);

        TableColumn<Student, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenume()));
        colPrenume.setPrefWidth(200);

        tableView.getColumns().addAll(colNume, colPrenume);


        return tableView;
    }

    private ObservableList<Student> getStudentGrup(int grupID) {
        ObservableList<Student> listStudenti = FXCollections.observableArrayList();
        String query = "{CALL VeziMembriGrup(?)}";

        try {
            PreparedStatement stmt = SqlConexiune.con.prepareStatement(query);
            stmt.setInt(1, grupID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listStudenti.add(new Student(
                            rs.getString("Nume"),
                            rs.getString("Prenume")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listStudenti;
    }
}
