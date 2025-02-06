package ro.platformaStudiu.professor;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.converter.DoubleStringConverter;
import ro.platformaStudiu.serviceClass.Exceptii.MarkException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.StudentProf;

import java.sql.*;



public class CatalogProfPage {
    private SqlConexiune conex;
    private ObservableList<StudentProf> studentData;
    private TableView<StudentProf> tableView;

    public CatalogProfPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume, int user_id, int profesorID) {
        this.conex=sql;
        Label headerLabel = new Label("Catalog Studenti:");
        headerLabel.setFont(new Font("Times New Roman", 20));
        headerLabel.setTextFill(Color.BLUE);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        tableView = initializeTableView();
        studentData = getStudentCatalog(profesorID);
        tableView.setItems(studentData);

        Button saveButton = new Button("Salvează Notele");
        saveButton.setFont(new Font("Times New Roman", 14));
        saveButton.setOnAction(e -> saveAllNotesToDatabase(statusLabel,profesorID));

        Button inapoi = new Button("Înapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new ProfessorPage(primaryStage, sql, nume, prenume, 2, user_id);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(headerLabel, tableView,statusLabel, saveButton, inapoi);

        Scene scene = new Scene(layout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catalog Studenti");
        primaryStage.show();

        refreshTableData(profesorID);
        primaryStage.show();
    }

    private void refreshTableData(int profesorID) {
        studentData = getStudentCatalog(profesorID);
        tableView.getItems().clear(); // Golește tabelul înainte de reîncărcare
        tableView.setItems(studentData); // Setează noile date
        tableView.refresh(); // Reîmprospătează vizualizarea
    }

    private TableView<StudentProf> initializeTableView() {
        TableView<StudentProf> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<StudentProf, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdStudent())));
        colId.setPrefWidth(100);


        TableColumn<StudentProf, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume()));
        colNume.setPrefWidth(200);


        TableColumn<StudentProf, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenume()));
        colPrenume.setPrefWidth(200);


        TableColumn<StudentProf, Double> colNotaCurs = new TableColumn<>("Nota Curs");
        colNotaCurs.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNotaCurs()).asObject());
        colNotaCurs.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colNotaCurs.setPrefWidth(150);


        colNotaCurs.setOnEditCommit(event -> {
            StudentProf student = event.getRowValue();
            student.setNotaCurs(event.getNewValue());
            tableView.refresh(); // Forțează reîmprospătarea vizuală a tabelului
        });


        TableColumn<StudentProf, Double> colNotaSeminar = new TableColumn<>("Nota Seminar");
        colNotaSeminar.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNotaSeminar()).asObject());
        colNotaSeminar.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colNotaSeminar.setPrefWidth(150);

        colNotaSeminar.setOnEditCommit(event -> {
            StudentProf student = event.getRowValue();
            student.setNotaSeminar(event.getNewValue());
            tableView.refresh(); // Reîmprospătare
        });

        TableColumn<StudentProf, Double> colNotaLaborator = new TableColumn<>("Nota Laborator");
        colNotaLaborator.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNotaLaborator()).asObject());
        colNotaLaborator.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colNotaLaborator.setPrefWidth(150);

        colNotaLaborator.setOnEditCommit(event -> {
            StudentProf student = event.getRowValue();
            student.setNotaLaborator(event.getNewValue());
            tableView.refresh(); // Reîmprospătare
        });


        tableView.getColumns().addAll(colId, colNume, colPrenume, colNotaCurs, colNotaSeminar, colNotaLaborator);

        return tableView;
    }



    private ObservableList<StudentProf> getStudentCatalog(int profesorId) {
        ObservableList<StudentProf> catalogData = FXCollections.observableArrayList();
        String query = "{CALL SelecteazaStudentiProfesor(?)}";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, profesorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    catalogData.add(new StudentProf(
                            rs.getInt("student_id"),
                            rs.getString("Nume"),
                            rs.getString("Prenume"),
                            rs.getDouble("Nota_Curs"),
                            rs.getDouble("Nota_Seminar"),
                            rs.getDouble("Nota_Laborator")

                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogData;
    }


    private void saveAllNotesToDatabase(Label statusLabel, int profesorID) {
        boolean success = true; // Indicator pentru verificarea operațiunilor de salvare
        int materieID = getMaterieProf(profesorID);

        for (StudentProf student : studentData) {
            try {

                valideazaNota(String.valueOf(student.getNotaCurs()), "Curs", String.valueOf(student.getIdStudent()));
                valideazaNota(String.valueOf(student.getNotaSeminar()), "Seminar", String.valueOf(student.getIdStudent()));
                valideazaNota(String.valueOf(student.getNotaLaborator()), "Laborator", String.valueOf(student.getIdStudent()));

                String query = "{CALL SetareNoteStudent(?, ?, ?, ?, ?, ?)}";

                try (CallableStatement stmt = SqlConexiune.con.prepareCall(query)) {
                    stmt.setDouble(1, student.getNotaSeminar());
                    stmt.setDouble(2, student.getNotaCurs());
                    stmt.setDouble(3, student.getNotaLaborator());
                    stmt.setInt(4, profesorID);
                    stmt.setString(5, String.valueOf(student.getIdStudent()));
                    stmt.setInt(6, materieID);

                    stmt.execute();


                    student.setNotaCurs(student.getNotaCurs());
                    student.setNotaSeminar(student.getNotaSeminar());
                    student.setNotaLaborator(student.getNotaLaborator());
                }
            } catch (MarkException e) {
                statusLabel.setText("Eroare!: " + e.getMessage());
                statusLabel.setTextFill(Color.RED);
                success = false;
            } catch (SQLException e) {
                statusLabel.setText("Eroare!: " + e.getMessage());
                statusLabel.setTextFill(Color.RED);
                e.printStackTrace();
                success = false;
            } catch (NumberFormatException e) {
                statusLabel.setText("Datele sunt incorecte pentru studentul cu ID: " + e.getMessage());
                statusLabel.setTextFill(Color.RED);
                success = false;
            }
        }

        if (success) {
            statusLabel.setText("Toate notele au fost introduse cu succes!");
            statusLabel.setTextFill(Color.GREEN);
            refreshTableData(profesorID);
            tableView.refresh();
        }



    }

    private int getMaterieProf(int profesorID){
        int materieID=0;
        String query = "{CALL GetMateriiProfesor(?)}";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, profesorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    materieID = rs.getInt("id_materii");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materieID;
    }


    private void valideazaNota(String nota, String tipNota, String idStudent) throws MarkException {
        try {
            double valoareNota = Double.parseDouble(nota);
            if (valoareNota < 0 || valoareNota > 10) {
                throw new MarkException("Nota " + tipNota + " pentru studentul cu ID " + idStudent + " trebuie să fie între 0 și 10.");
            }
        } catch (NumberFormatException e) {
            throw new MarkException("Nota " + tipNota + " pentru studentul cu ID " + idStudent + " este invalidă. Introdu o valoare numerică.");
        }
    }


}
