package ro.platformaStudiu.student.catalog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.student.StudentPage;

import java.sql.*;

public class CatalogPage {

    private SqlConexiune conex;
    private int student_id;

    public CatalogPage(Stage primaryStage, SqlConexiune sql,String nume,String prenume,int user_id, int student_id) {
        this.conex = sql;
        this.student_id = student_id;

        Label headerLabel = new Label("Note:");
        headerLabel.setFont(new Font("Times New Roman", 30));
        headerLabel.setTextFill(Color.BLUE);


        TableView<Catalog> tableView = createTableView();
        ObservableList<Catalog> data = getStudentCatalog(student_id);
        tableView.setItems(data);

        Button inapoi = new Button("Înapoi");
        inapoi.setFont(new Font(18));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new StudentPage(primaryStage, sql, nume, prenume,user_id);
        });

        VBox topLayout = new VBox(10);
        topLayout.setPadding(new Insets(10));
        topLayout.getChildren().addAll(headerLabel, inapoi);

        // Layout principal
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topLayout);
        mainLayout.setCenter(tableView);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catalog Student");
        primaryStage.show();
    }

    private TableView<Catalog> createTableView() {
        TableView<Catalog> tableView = new TableView<>();

        TableColumn<Catalog, String> materieCol = new TableColumn<>("Materie");
        materieCol.setCellValueFactory(cellData -> cellData.getValue().materieProperty());
        materieCol.setPrefWidth(200);

        TableColumn<Catalog, String> notaCol = new TableColumn<>("Nota");
        notaCol.setCellValueFactory(cellData -> cellData.getValue().notaProperty());

        tableView.getColumns().addAll(materieCol, notaCol);

        return tableView;
    }

    private ObservableList<Catalog> getStudentCatalog(int student_id) {
        ObservableList<Catalog> catalogData = FXCollections.observableArrayList();
        String query = "{CALL GetStudentNote(?)}";

        try {
            PreparedStatement stmt = SqlConexiune.con.prepareStatement(query);
            stmt.setInt(1, student_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    catalogData.add(new Catalog(
                            rs.getString("Materie"),
                            String.valueOf(rs.getDouble("Nota"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return catalogData;
    }

}
