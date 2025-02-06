package ro.platformaStudiu;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.LoginPage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SqlConexiune sql= new SqlConexiune();
        sql.init();
        new LoginPage(stage, sql);

    }
}