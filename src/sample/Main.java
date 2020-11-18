package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static String url="jdbc:sqlite:/Users/baharsadik/Desktop/Datalogi/data.db";
    static StudentModel SDB = new StudentModel(url);

    @Override
    public void start(Stage primaryStage) throws Exception{

        Controller control = new Controller(SDB);
        StudentView view = new StudentView(SDB,control);
        control.setView(view);

        primaryStage.setTitle("Student Form");
        primaryStage.setScene(new Scene(view.asParent(), 500, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
